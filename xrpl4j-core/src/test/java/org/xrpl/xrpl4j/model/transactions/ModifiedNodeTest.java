package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.UnsignedInteger;
import org.junit.jupiter.api.Test;
import org.xrpl.xrpl4j.model.AbstractJsonTest;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.flags.AccountRootFlags;
import org.xrpl.xrpl4j.model.flags.RippleStateFlags;
import org.xrpl.xrpl4j.model.ledger.ImmutableAccountRootObject;
import org.xrpl.xrpl4j.model.ledger.ImmutableRippleStateObject;
import org.xrpl.xrpl4j.model.ledger.LedgerObject;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class ModifiedNodeTest extends AbstractJsonTest {
    @Test
    public void deserialize() throws JsonProcessingException {

        String json = "{\n" +
                "  \"AffectedNodes\": [\n" +
                "    {\n" +
                "      \"ModifiedNode\": {\n" +
                "        \"FinalFields\": {\n" +
                "          \"Balance\": {\n" +
                "            \"currency\": \"FOO\",\n" +
                "            \"issuer\": \"rrrrrrrrrrrrrrrrrrrrBZbvji\",\n" +
                "            \"value\": \"-100000100\"\n" +
                "          },\n" +
                "          \"Flags\": 131072,\n" +
                "          \"HighLimit\": {\n" +
                "            \"currency\": \"FOO\",\n" +
                "            \"issuer\": \"raBzjVy7WJMqUgdBqAWDAEH1Z3acWK9u6c\",\n" +
                "            \"value\": \"10000000000\"\n" +
                "          },\n" +
                "          \"HighNode\": \"0\",\n" +
                "          \"LowLimit\": {\n" +
                "            \"currency\": \"FOO\",\n" +
                "            \"issuer\": \"rphDLpCwJyPEBuZccdBCWPUgAXUd96dUCJ\",\n" +
                "            \"value\": \"0\"\n" +
                "          },\n" +
                "          \"LowNode\": \"0\"\n" +
                "        },\n" +
                "        \"LedgerEntryType\": \"RippleState\",\n" +
                "        \"LedgerIndex\": \"CF2A10BB2DD216A456421A2AA1CB6CD35B18E36A0BE80976772CCCEA08EBAC97\",\n" +
                "        \"PreviousFields\": {\n" +
                "          \"Balance\": {\n" +
                "            \"currency\": \"FOO\",\n" +
                "            \"issuer\": \"rrrrrrrrrrrrrrrrrrrrBZbvji\",\n" +
                "            \"value\": \"-100000000\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"PreviousTxnID\": \"0D964F03758AAD5C0763F343FEF020C9820FC61832F861AE2C92B97C7FAC389D\",\n" +
                "        \"PreviousTxnLgrSeq\": 57179\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"ModifiedNode\": {\n" +
                "        \"FinalFields\": {\n" +
                "          \"Account\": \"rphDLpCwJyPEBuZccdBCWPUgAXUd96dUCJ\",\n" +
                "          \"Balance\": \"29999999940\",\n" +
                "          \"Flags\": 8388608,\n" +
                "          \"OwnerCount\": 0,\n" +
                "          \"Sequence\": 57013\n" +
                "        },\n" +
                "        \"LedgerEntryType\": \"AccountRoot\",\n" +
                "        \"LedgerIndex\": \"EC6AA5542F739EE340315EAC0B67ABCC8B5533DD683AE3C9AD1C311240D86317\",\n" +
                "        \"PreviousFields\": {\n" +
                "          \"Balance\": \"29999999950\",\n" +
                "          \"Sequence\": 57012\n" +
                "        },\n" +
                "        \"PreviousTxnID\": \"D1AD67D98F160A7AA518E969803393DEFE0F0132E66205230021DFDDAAE58AA4\",\n" +
                "        \"PreviousTxnLgrSeq\": 109607\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"TransactionIndex\": 0,\n" +
                "  \"TransactionResult\": \"tesSUCCESS\",\n" +
                "  \"delivered_amount\": {\n" +
                "    \"currency\": \"FOO\",\n" +
                "    \"issuer\": \"rphDLpCwJyPEBuZccdBCWPUgAXUd96dUCJ\",\n" +
                "    \"value\": \"100\"\n" +
                "  }\n" +
                "}";
        TransactionMetadata deserialized = objectMapper.readValue(json, TransactionMetadata.class);

        ImmutableRippleStateObject.Builder ripleStateObjectBuilder = ImmutableRippleStateObject.builder()
                .balance(IssuedCurrencyAmount.builder()
                        .currency("FOO")
                        .issuer(Address.of("rrrrrrrrrrrrrrrrrrrrBZbvji"))
                        .value("-100000100")
                        .build())
                .flags(RippleStateFlags.of(131072))
                .highLimit(IssuedCurrencyAmount.builder()
                        .currency("FOO")
                        .issuer(Address.of("raBzjVy7WJMqUgdBqAWDAEH1Z3acWK9u6c"))
                        .value("10000000000")
                        .build())
                .highNode("0")
                .lowLimit(IssuedCurrencyAmount.builder()
                        .currency("FOO")
                        .issuer(Address.of("rphDLpCwJyPEBuZccdBCWPUgAXUd96dUCJ"))
                        .value("0")
                        .build())
                .lowNode("0")
                .previousTransactionId(Hash256.of("0D964F03758AAD5C0763F343FEF020C9820FC61832F861AE2C92B97C7FAC389D"))
                .previousTransactionLedgerSequence(UnsignedInteger.valueOf(57179))
                .index(Hash256.of("CF2A10BB2DD216A456421A2AA1CB6CD35B18E36A0BE80976772CCCEA08EBAC97"));

        ImmutableAccountRootObject.Builder accountRootBuilder = ImmutableAccountRootObject.builder()
                .account(Address.of("rphDLpCwJyPEBuZccdBCWPUgAXUd96dUCJ"))
                .balance(XrpCurrencyAmount.of(BigInteger.valueOf(29999999940L)))
                .flags(AccountRootFlags.of(8388608))
                .ownerCount(UnsignedInteger.valueOf(0))
                .sequence(UnsignedInteger.valueOf(57013))
                .previousTransactionId(Hash256.of("D1AD67D98F160A7AA518E969803393DEFE0F0132E66205230021DFDDAAE58AA4"))
                .previousTransactionLedgerSequence(UnsignedInteger.valueOf(109607))
                .index(Hash256.of("EC6AA5542F739EE340315EAC0B67ABCC8B5533DD683AE3C9AD1C311240D86317"));
        assertThat(deserialized.affectedNodes()).isEqualTo(ImmutableList.of(
                ImmutableModifiedNode.builder()
                        .ledgerEntryType(LedgerObject.LedgerEntryType.RIPPLE_STATE)
                        .ledgerIndex(Hash256.of("CF2A10BB2DD216A456421A2AA1CB6CD35B18E36A0BE80976772CCCEA08EBAC97"))
                        .previousTransactionId(Hash256.of("0D964F03758AAD5C0763F343FEF020C9820FC61832F861AE2C92B97C7FAC389D"))
                        .previousTransactionLedgerSequence(LedgerIndex.of(UnsignedInteger.valueOf(57179)))
                        .finalFields(ripleStateObjectBuilder
                                .build())
                        .previousFields(ripleStateObjectBuilder
                                .balance(IssuedCurrencyAmount.builder()
                                        .currency("FOO")
                                        .issuer(Address.of("rrrrrrrrrrrrrrrrrrrrBZbvji"))
                                        .value("-100000000")
                                        .build())
                                .build())
                        .build(),
                ImmutableModifiedNode.builder()
                        .ledgerEntryType(LedgerObject.LedgerEntryType.ACCOUNT_ROOT)
                        .ledgerIndex(Hash256.of("EC6AA5542F739EE340315EAC0B67ABCC8B5533DD683AE3C9AD1C311240D86317"))
                        .previousTransactionId(Hash256.of("D1AD67D98F160A7AA518E969803393DEFE0F0132E66205230021DFDDAAE58AA4"))
                        .previousTransactionLedgerSequence(LedgerIndex.of(UnsignedInteger.valueOf(109607)))
                        .finalFields(accountRootBuilder
                                .build())
                        .previousFields(accountRootBuilder
                                .balance(XrpCurrencyAmount.of(BigInteger.valueOf(29999999950L)))
                                .sequence(UnsignedInteger.valueOf(57012))
                                .build())
                        .build()
        ));
    }

}