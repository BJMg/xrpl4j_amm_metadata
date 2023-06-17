package org.xrpl.xrpl4j.model.ledger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Strings;
import com.google.common.primitives.UnsignedInteger;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.xrpl.xrpl4j.model.AbstractJsonTest;
import org.xrpl.xrpl4j.model.transactions.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class AmmObjectTest extends AbstractJsonTest {

  @Test
  void voteSlotsUnwrapped() {
    VoteEntry voteEntry1 = VoteEntry.builder()
      .account(Address.of("rE54zDvgnghAoPopCgvtiqWNq3dU5y836S"))
      .voteWeight(VoteWeight.of(UnsignedInteger.ONE))
      .tradingFee(TradingFee.of(UnsignedInteger.ONE))
      .build();
    VoteEntry voteEntry2 = VoteEntry.builder()
      .account(Address.of("rP9jPyP5kyvFRb6ZiRghAGw5u8SGAmU4bd"))
      .voteWeight(VoteWeight.of(UnsignedInteger.valueOf(2)))
      .tradingFee(TradingFee.of(UnsignedInteger.valueOf(2)))
      .build();
    AmmObject ammObject = AmmObject.builder()
      .asset(mock(Asset.class))
      .asset2(mock(Asset.class))
      .ammAccount(Address.of("rP9jPyP5kyvFRb6ZiRghAGw5u8SGAmU4bd"))
      .lpTokenBalance(mock(IssuedCurrencyAmount.class))
      .tradingFee(TradingFee.of(UnsignedInteger.ONE))
      .addVoteSlots(
        VoteEntryWrapper.of(voteEntry1),
        VoteEntryWrapper.of(voteEntry2)
      )
      .index(Hash256.of(Strings.repeat("0", 64)))
      .build();

    assertThat(ammObject.voteSlotsUnwrapped()).asList()
      .containsExactlyInAnyOrder(voteEntry1, voteEntry2);
  }

  @Test
  void testJson() throws JSONException, JsonProcessingException {
    AmmObject ammObject = AmmObject.builder()
      .asset(Asset.XRP)
      .asset2(
        Asset.builder()
          .currency("TST")
          .issuer(Address.of("rP9jPyP5kyvFRb6ZiRghAGw5u8SGAmU4bd"))
          .build()
      )
      .ammAccount(Address.of("rE54zDvgnghAoPopCgvtiqWNq3dU5y836S"))
      .lpTokenBalance(
        IssuedCurrencyAmount.builder()
          .value("71150.53584131501")
          .currency("039C99CD9AB0B70B32ECDA51EAAE471625608EA2")
          .issuer(Address.of("rE54zDvgnghAoPopCgvtiqWNq3dU5y836S"))
          .build()
      )
      .tradingFee(TradingFee.of(UnsignedInteger.valueOf(600)))
      .addVoteSlots(
        VoteEntryWrapper.of(
          VoteEntry.builder()
            .account(Address.of("rJVUeRqDFNs2xqA7ncVE6ZoAhPUoaJJSQm"))
            .voteWeight(VoteWeight.of(UnsignedInteger.valueOf(100000)))
            .tradingFee(TradingFee.of(UnsignedInteger.valueOf(600)))
            .build()
        )
      )
      .auctionSlot(
        AuctionSlot.builder()
          .account(Address.of("rJVUeRqDFNs2xqA7ncVE6ZoAhPUoaJJSQm"))
          .addAuthAccounts(
            AuthAccountWrapper.of(
              AuthAccount.of(
                Address.of("rMKXGCbJ5d8LbrqthdG46q3f969MVK2Qeg")
              )
            ),
            AuthAccountWrapper.of(
              AuthAccount.of(
                Address.of("rBepJuTLFJt3WmtLXYAxSjtBWAeQxVbncv")
              )
            )
          )
          .discountedFee(TradingFee.of(UnsignedInteger.ZERO))
          .price(
            IssuedCurrencyAmount.builder()
              .value("0.8696263565463045")
              .currency("039C99CD9AB0B70B32ECDA51EAAE471625608EA2")
              .issuer(Address.of("rE54zDvgnghAoPopCgvtiqWNq3dU5y836S"))
              .build()
          )
          .expiration(UnsignedInteger.valueOf(721870180))
          .build()
      )
      .index(Hash256.of(Strings.repeat("0", 64)))
      .build();

    String json = "{\n" +
      "    \"AMMAccount\" : \"rE54zDvgnghAoPopCgvtiqWNq3dU5y836S\",\n" +
      "    \"LedgerEntryType\" : \"AMM\",\n" +
      "    \"Asset\" : " + objectMapper.writeValueAsString(ammObject.asset())  + "," +
      "    \"Asset2\" : " + objectMapper.writeValueAsString(ammObject.asset2())  + "," +
      "    \"AuctionSlot\" : " + objectMapper.writeValueAsString(ammObject.auctionSlot()) + "," +
      "    \"Flags\" : 0,\n" +
      "    \"LPTokenBalance\" : " + objectMapper.writeValueAsString(ammObject.lpTokenBalance()) + "," +
      "    \"TradingFee\" : 600,\n" +
      "    \"VoteSlots\" : [\n" +
              objectMapper.writeValueAsString(ammObject.voteSlots().get(0)) +
      "    ],\n" +
      "    \"index\" : " + objectMapper.writeValueAsString(Strings.repeat("0", 64)) +
      "}";

    assertCanSerializeAndDeserialize(ammObject, json);
  }
}