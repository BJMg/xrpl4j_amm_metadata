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
                LedgerObject.LedgerEntryType ledgerEntryType = LedgerObject.LedgerEntryType.forValueIfExists(ledgerEntryTypeNode.textValue());
                JsonNode ledgerIndex = objectNode.get("LedgerIndex");

                ImmutableDeletedNode.Builder builder = ImmutableDeletedNode.builder()
                        .ledgerIndex(Hash256.of(ledgerIndex.textValue()));
                if (ledgerEntryType != null) {
                    if (ledgerObjectDeserializeCache.canBeDeserialized(ledgerEntryType)) {
                        ObjectNode finalFieldsNode = (ObjectNode) objectNode.required("FinalFields");
                        finalFieldsNode.set("index", ledgerIndex);
                        finalFieldsNode.set("LedgerEntryType", ledgerEntryTypeNode);
                        builder.finalFields(Optional.ofNullable(deserializationContext.readTreeAsValue(finalFieldsNode, LedgerObject.class)));
                    }
                    builder.ledgerEntryType(ledgerEntryType);
                }
                return builder
                        .build();
            } else {
                return null;
            }
        }
    }
}
