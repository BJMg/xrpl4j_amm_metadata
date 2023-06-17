package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.ledger.LedgerObject;

import java.io.IOException;
import java.util.Optional;

import static org.xrpl.xrpl4j.model.transactions.ModifiedNode.Deserializer.EMPTY_HASH_NODE;
import static org.xrpl.xrpl4j.model.transactions.ModifiedNode.Deserializer.EMPTY_LONG_NODE;

/**
 * A {@link CreatedNode} contains the objects in the ledger that a transaction created.
 *
 * @see <a href="https://xrpl.org/transaction-metadata.html#creatednode-fields">
 * https://xrpl.org/transaction-metadata.html#creatednode-fields</a>
 */
@Value.Immutable
@JsonDeserialize(using = CreatedNode.Deserializer.class)
@JsonTypeName("CreatedNode")
public interface CreatedNode extends AffectedNode {

    /**
     * The content fields of the newly-created ledger object.
     *
     * @return The content fields of the newly-created ledger object.
     */
    Optional<LedgerObject> newFields();


    class Deserializer extends StdDeserializer<ImmutableCreatedNode> {

        private final LedgerObjectDeserializeCache ledgerObjectDeserializeCache = LedgerObjectDeserializeCache.getDefault();

        protected Deserializer() {
            super(ImmutableCreatedNode.class);
        }

        @Override
        public ImmutableCreatedNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                JsonNode ledgerEntryTypeNode = objectNode.get("LedgerEntryType");
                LedgerObject.LedgerEntryType ledgerEntryType = LedgerObject.LedgerEntryType.forValue(ledgerEntryTypeNode.textValue());
                ObjectNode newFieldsNode = (ObjectNode) objectNode.required("NewFields");
                JsonNode ledgerIndex = objectNode.get("LedgerIndex");
                newFieldsNode.set("index", ledgerIndex);
                newFieldsNode.set("LedgerEntryType", ledgerEntryTypeNode);
                newFieldsNode.set("PreviousTxnLgrSeq", EMPTY_LONG_NODE);
                newFieldsNode.set("PreviousTxnID", EMPTY_HASH_NODE);

                ImmutableCreatedNode.Builder builder = ImmutableCreatedNode.builder();
                if (ledgerObjectDeserializeCache.canBeDeserialized(ledgerEntryType)) {
                    builder.newFields(Optional.ofNullable(deserializationContext.readTreeAsValue(newFieldsNode, LedgerObject.class)));
                }
                return builder
                        .ledgerIndex(Hash256.of(ledgerIndex.textValue()))
                        .ledgerEntryType(ledgerEntryType)
                        .build();
            } else {
                return null;
            }
        }
    }
}
