package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 * A {@link DeletedNode} contains the objects in the ledger that a transaction deleted.
 *
 * @see <a href="https://xrpl.org/transaction-metadata.html#deletednode-fields">
 * https://xrpl.org/transaction-metadata.html#deletednode-fields</a>
 */
@Value.Immutable
@JsonDeserialize(using = DeletedNode.Deserializer.class)
@JsonTypeName("DeletedNode")
public interface DeletedNode extends AffectedNode {

    /**
     * @return The content fields of the ledger object immediately before it was deleted.
     */
    @JsonProperty("FinalFields")
    Optional<LedgerObject> finalFields();


    class Deserializer extends StdDeserializer<ImmutableDeletedNode> {

        private final LedgerObjectDeserializeCache ledgerObjectDeserializeCache = LedgerObjectDeserializeCache.getDefault();

        protected Deserializer() {
            super(ImmutableDeletedNode.class);
        }

        @Override
        public ImmutableDeletedNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                JsonNode ledgerEntryTypeNode = objectNode.get("LedgerEntryType");
                LedgerObject.LedgerEntryType ledgerEntryType = LedgerObject.LedgerEntryType.forValue(ledgerEntryTypeNode.textValue());
                ObjectNode finalFieldsNode = (ObjectNode) objectNode.required("FinalFields");
                JsonNode ledgerIndex = objectNode.get("LedgerIndex");
                finalFieldsNode.set("index", ledgerIndex);
                finalFieldsNode.set("LedgerEntryType", ledgerEntryTypeNode);
                if (!finalFieldsNode.has("PreviousTxnLgrSeq")) {
                    finalFieldsNode.set("PreviousTxnLgrSeq", EMPTY_LONG_NODE);
                }
                if (!finalFieldsNode.has("PreviousTxnID")) {
                    finalFieldsNode.set("PreviousTxnID", EMPTY_HASH_NODE);
                }

                ImmutableDeletedNode.Builder builder = ImmutableDeletedNode.builder();
                if (ledgerObjectDeserializeCache.canBeDeserialized(ledgerEntryType)) {
                    builder.finalFields(Optional.ofNullable(deserializationContext.readTreeAsValue(finalFieldsNode, LedgerObject.class)));
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
