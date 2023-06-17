package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.UnsignedInteger;
import org.junit.jupiter.api.Test;
import org.xrpl.xrpl4j.model.AbstractJsonTest;
import org.xrpl.xrpl4j.model.flags.RippleStateFlags;
import org.xrpl.xrpl4j.model.ledger.ImmutableRippleStateObject;
import org.xrpl.xrpl4j.model.ledger.LedgerObject;

import static org.assertj.core.api.Assertions.assertThat;

class CreatedNodeTest extends AbstractJsonTest {
    @Test
    public void deserialize() throws JsonProcessingException {

        String json = "{\n" +
                "  \"AffectedNodes\": [\n" +
                "    {\n" +
                "      \"CreatedNode\": {\n" +
                "        \"LedgerEntryType\": \"DirectoryNode\",\n" +
                "        \"LedgerIndex\": \"2F3A62FD82453F130039DA50D21A2850C073F946795F1B174445DD32791F8593\",\n" +
                "        \"NewFields\": {\n" +
                "          \"Owner\": \"rphDLpCwJyPEBuZccdBCWPUgAXUd96dUCJ\",\n" +
                "          \"RootIndex\": \"2F3A62FD82453F130039DA50D21A2850C073F946795F1B174445DD32791F8593\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"CreatedNode\": {\n" +
                "        \"LedgerEntryType\": \"DirectoryNode\",\n" +
                "        \"LedgerIndex\": \"562E5CE7EEFFE72917D3836B510F1150C25586826FA4134953E11BF4A5BC4E05\",\n" +
                "        \"NewFields\": {\n" +
                "          \"Owner\": \"raBzjVy7WJMqUgdBqAWDAEH1Z3acWK9u6c\",\n" +
                "          \"RootIndex\": \"562E5CE7EEFFE72917D3836B510F1150C25586826FA4134953E11BF4A5BC4E05\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"CreatedNode\": {\n" +
                "        \"LedgerEntryType\": \"RippleState\",\n" +
                "        \"LedgerIndex\": \"CF2A10BB2DD216A456421A2AA1CB6CD35B18E36A0BE80976772CCCEA08EBAC97\",\n" +
                "        \"NewFields\": {\n" +
                "          \"Balance\": {\n" +
                "            \"currency\": \"FOO\",\n" +
                "            \"issuer\": \"rrrrrrrrrrrrrrrrrrrrBZbvji\",\n" +
                "            \"value\": \"0\"\n" +
                "          },\n" +
                "          \"Flags\": 131072,\n" +
                "          \"HighLimit\": {\n" +
                "            \"currency\": \"FOO\",\n" +
                "            \"issuer\": \"raBzjVy7WJMqUgdBqAWDAEH1Z3acWK9u6c\",\n" +
                "            \"value\": \"10000000000\"\n" +
                "          },\n" +
                "          \"LowLimit\": {\n" +
                "            \"currency\": \"FOO\",\n" +
                "            \"issuer\": \"rphDLpCwJyPEBuZccdBCWPUgAXUd96dUCJ\",\n" +
                "            \"value\": \"0\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"TransactionIndex\": 0,\n" +
                "  \"TransactionResult\": \"tesSUCCESS\"\n" +
                "}";
        TransactionMetadata deserialized = objectMapper.readValue(json, TransactionMetadata.class);

        assertThat(deserialized.affectedNodes()).isEqualTo(ImmutableList.of(
                ImmutableCreatedNode.builder()
                        .ledgerEntryType(LedgerObject.LedgerEntryType.DIRECTORY_NODE)
                        .ledgerIndex(Hash256.of("2F3A62FD82453F130039DA50D21A2850C073F946795F1B174445DD32791F8593"))
                        .build(),
                ImmutableCreatedNode.builder()
                        .ledgerEntryType(LedgerObject.LedgerEntryType.DIRECTORY_NODE)
                        .ledgerIndex(Hash256.of("562E5CE7EEFFE72917D3836B510F1150C25586826FA4134953E11BF4A5BC4E05"))
                        .build(),
                ImmutableCreatedNode.builder()
                        .ledgerEntryType(LedgerObject.LedgerEntryType.RIPPLE_STATE)
                        .ledgerIndex(Hash256.of("CF2A10BB2DD216A456421A2AA1CB6CD35B18E36A0BE80976772CCCEA08EBAC97"))
                        .newFields(ImmutableRippleStateObject.builder()
                                .balance(IssuedCurrencyAmount.builder()
                                        .currency("FOO")
                                        .issuer(Address.of("rrrrrrrrrrrrrrrrrrrrBZbvji"))
                                        .value("0")
                                        .build())
                                .flags(RippleStateFlags.of(131072))
                                .highLimit(IssuedCurrencyAmount.builder()
                                        .currency("FOO")
                                        .issuer(Address.of("raBzjVy7WJMqUgdBqAWDAEH1Z3acWK9u6c"))
                                        .value("10000000000")
                                        .build())
                                .lowLimit(IssuedCurrencyAmount.builder()
                                        .currency("FOO")
                                        .issuer(Address.of("rphDLpCwJyPEBuZccdBCWPUgAXUd96dUCJ"))
                                        .value("0")
                                        .build())
                                .previousTransactionId(Hash256.of("0000000000000000000000000000000000000000000000000000000000000000"))
                                .previousTransactionLedgerSequence(UnsignedInteger.valueOf(0))
                                .index(Hash256.of("CF2A10BB2DD216A456421A2AA1CB6CD35B18E36A0BE80976772CCCEA08EBAC97"))
                                .build())
                        .build()
        ));
    }

}