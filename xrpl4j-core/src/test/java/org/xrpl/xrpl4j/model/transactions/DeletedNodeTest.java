package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.UnsignedInteger;
import org.junit.jupiter.api.Test;
import org.xrpl.xrpl4j.model.AbstractJsonTest;
import org.xrpl.xrpl4j.model.flags.OfferFlags;
import org.xrpl.xrpl4j.model.ledger.ImmutableOfferObject;
import org.xrpl.xrpl4j.model.ledger.LedgerObject;

import static org.assertj.core.api.Assertions.assertThat;

class DeletedNodeTest  extends AbstractJsonTest {
    @Test
    public void deserialize() throws JsonProcessingException {

        String json = "{\n" +
                "  \"AffectedNodes\": [\n" +
                "    {\n" +
                "      \"DeletedNode\": {\n" +
                "        \"FinalFields\": {\n" +
                "          \"Account\": \"r9ZoLsJHzMMJLpvsViWQ4Jgx17N8cz1997\",\n" +
                "          \"BookDirectory\": \"A6D5D1C1CC92D56FDDFD4434FB10BD31F63EB991DA3C756653071AFD498D0000\",\n" +
                "          \"BookNode\": \"0000000000000000\",\n" +
                "          \"Flags\": 0,\n" +
                "          \"OwnerNode\": \"0000000000000000\",\n" +
                "          \"PreviousTxnID\": \"DB028A461E98B0398CAD65F2871B381A6D0B9A21662CA5B033438D83C518C0F2\",\n" +
                "          \"PreviousTxnLgrSeq\": 35686129,\n" +
                "          \"Sequence\": 7,\n" +
                "          \"TakerGets\": {\n" +
                "            \"currency\": \"EUR\",\n" +
                "            \"issuer\": \"rhub8VRN55s94qWKDv6jmDy1pUykJzF3wq\",\n" +
                "            \"value\": \"2.5\"\n" +
                "          },\n" +
                "          \"TakerPays\": {\n" +
                "            \"currency\": \"ETH\",\n" +
                "            \"issuer\": \"rcA8X3TVMST1n3CJeAdGk1RdRCHii7N2h\",\n" +
                "            \"value\": \"0.05\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"LedgerEntryType\": \"Offer\",\n" +
                "        \"LedgerIndex\": \"6AA7E5121FEB456F0A899E3D6F25D62ABB408BB67B91C9270E13714401ED72B5\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"DeliveredAmount\": {\n" +
                "    \"currency\": \"GCB\",\n" +
                "    \"issuer\": \"rHaans8PtgwbacHvXAL3u6TG28gTAtCwr8\",\n" +
                "    \"value\": \"2.788706\"\n" +
                "  },\n" +
                "  \"TransactionIndex\": 38,\n" +
                "  \"TransactionResult\": \"tesSUCCESS\",\n" +
                "  \"delivered_amount\": {\n" +
                "    \"currency\": \"GCB\",\n" +
                "    \"issuer\": \"rHaans8PtgwbacHvXAL3u6TG28gTAtCwr8\",\n" +
                "    \"value\": \"2.788706\"\n" +
                "  }\n" +
                "}";
        TransactionMetadata deserialized = objectMapper.readValue(json, TransactionMetadata.class);

        assertThat(deserialized.affectedNodes()).isEqualTo(ImmutableList.of(
                ImmutableDeletedNode.builder()
                        .ledgerEntryType(LedgerObject.LedgerEntryType.OFFER)
                        .ledgerIndex(Hash256.of("6AA7E5121FEB456F0A899E3D6F25D62ABB408BB67B91C9270E13714401ED72B5"))
                        .finalFields(ImmutableOfferObject.builder()
                                .account(Address.of("r9ZoLsJHzMMJLpvsViWQ4Jgx17N8cz1997"))
                                .bookDirectory(Hash256.of("A6D5D1C1CC92D56FDDFD4434FB10BD31F63EB991DA3C756653071AFD498D0000"))
                                .bookNode("0000000000000000")
                                .flags(OfferFlags.of(0))
                                .ownerNode("0000000000000000")
                                .previousTransactionId(Hash256.of("DB028A461E98B0398CAD65F2871B381A6D0B9A21662CA5B033438D83C518C0F2"))
                                .previousTransactionLedgerSequence(UnsignedInteger.valueOf(35686129))
                                .sequence(UnsignedInteger.valueOf(7))
                                .index(Hash256.of("6AA7E5121FEB456F0A899E3D6F25D62ABB408BB67B91C9270E13714401ED72B5"))
                                .takerGets(ImmutableIssuedCurrencyAmount.builder()
                                        .currency("EUR")
                                        .issuer(Address.of("rhub8VRN55s94qWKDv6jmDy1pUykJzF3wq"))
                                        .value("2.5")
                                        .build())
                                .takerPays(ImmutableIssuedCurrencyAmount.builder()
                                        .currency("ETH")
                                        .issuer(Address.of("rcA8X3TVMST1n3CJeAdGk1RdRCHii7N2h"))
                                        .value("0.05")
                                        .build())
                                .build())
                        .build()
        ));
    }

}