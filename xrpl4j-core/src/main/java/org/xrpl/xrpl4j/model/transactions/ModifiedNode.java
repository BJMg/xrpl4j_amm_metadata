package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.primitives.UnsignedInteger;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.ledger.LedgerObject;

import java.io.IOException;
import java.util.Optional;

import static com.google.common.base.MoreObjects.firstNonNull;

/**
 * A {@link ModifiedNode} contains the objects in the ledger that a transaction modified in some way.
 *
 * @see <a href="https://xrpl.org/transaction-metadata.html#modifiednode-fields">
 * https://xrpl.org/transaction-metadata.html#modifiednode-fields</a>
 */
@Value.Immutable
@JsonDeserialize(using = ModifiedNode.Deserializer.class)
@JsonTypeName("ModifiedNode")
public interface ModifiedNode extends AffectedNode {

    /**
     * FinalFields contain the content fields of the ledger object after applying any changes from this transaction.
     *
     * @return The content fields of the ledger object after applying any changes from this transaction.
     */
    Optional<LedgerObject> finalFields();

    /**
     * The previous values for all fields of the object that were changed as a result of this transaction.
     *
     * @return The previous values for all fields of the object that were changed as a result of this transaction.
     */
    Optional<LedgerObject> previousFields();

    /**
     * The identifying hash of the previous transaction to modify this ledger object.
     *
     * @return The identifying hash of the previous transaction to modify this ledger object.
     */
    Optional<Hash256> previousTransactionId();

    /**
     * The {@link LedgerIndex} of the ledger version containing the previous
     * transaction to modify this ledger object.
     *
     * @return The {@link LedgerIndex} of the ledger version containing the previous
     * transaction to modify this ledger object.
     */
    Optional<LedgerIndex> previousTransactionLedgerSequence();

    class Deserializer extends StdDeserializer<ImmutableModifiedNode> {

        public static TextNode EMPTY_HASH_NODE = new TextNode("0000000000000000000000000000000000000000000000000000000000000000");
        public static LongNode EMPTY_LONG_NODE = LongNode.valueOf(0);

        private final LedgerObjectDeserializeCache ledgerObjectDeserializeCache = LedgerObjectDeserializeCache.getDefault();

        protected Deserializer() {
            super(ImmutableModifiedNode.class);
        }

        @Override
        public ImmutableModifiedNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                JsonNode previousTxnLgrSeq = firstNonNull(objectNode.get("PreviousTxnLgrSeq"), EMPTY_LONG_NODE);
                JsonNode previousTxnId = firstNonNull(objectNode.get("PreviousTxnID"), EMPTY_HASH_NODE);
                JsonNode ledgerEntryTypeNode = objectNode.get("LedgerEntryType");
                JsonNode ledgerIndex = objectNode.get("LedgerIndex");

                LedgerObject.LedgerEntryType ledgerEntryType = LedgerObject.LedgerEntryType.forValue(ledgerEntryTypeNode.textValue());

                ImmutableModifiedNode.Builder builder = ImmutableModifiedNode.builder();
                JsonNode finalFieldsJsonNode = objectNode.get("FinalFields");
                if (ledgerObjectDeserializeCache.canBeDeserialized(ledgerEntryType) && finalFieldsJsonNode != null) {

                    ObjectNode finalFieldsNode = (ObjectNode) finalFieldsJsonNode;
                    finalFieldsNode.set("index", ledgerIndex);
                    finalFieldsNode.set("LedgerEntryType", ledgerEntryTypeNode);
                    finalFieldsNode.set("PreviousTxnLgrSeq", previousTxnLgrSeq);
                    finalFieldsNode.set("PreviousTxnID", previousTxnId);
                    builder.finalFields(Optional.ofNullable(deserializationContext.readTreeAsValue(finalFieldsNode, LedgerObject.class)));

                    JsonNode previousFieldsNode = objectNode.get("PreviousFields");
                    if (previousFieldsNode != null && previousFieldsNode.isObject()) {
                        finalFieldsNode.setAll((ObjectNode) previousFieldsNode);
                        LedgerObject previousFields = deserializationContext.readTreeAsValue(finalFieldsNode, LedgerObject.class);
                        builder.previousFields(previousFields);
                    }
                }

                builder.previousTransactionId(Hash256.of(previousTxnId.textValue()));
                builder.previousTransactionLedgerSequence(LedgerIndex.of(UnsignedInteger.valueOf(previousTxnLgrSeq.longValue())));
                builder.ledgerIndex(Hash256.of(ledgerIndex.textValue()));
                builder.ledgerEntryType(ledgerEntryType);
                return builder.build();
            } else {
                return null;
            }
        }
    }
}
