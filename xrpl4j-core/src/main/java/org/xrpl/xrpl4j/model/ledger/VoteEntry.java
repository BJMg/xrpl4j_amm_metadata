package org.xrpl.xrpl4j.model.ledger;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.UnsignedInteger;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.TradingFee;
import org.xrpl.xrpl4j.model.transactions.VoteWeight;

/**
 * Describes a vote for the trading fee on an AMM by an LP.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableVoteEntry.class)
@JsonDeserialize(as = ImmutableVoteEntry.class)
public interface VoteEntry {

  /**
   * Construct a {@code VoteEntry} builder.
   *
   * @return An {@link ImmutableVoteEntry.Builder}.
   */
  static ImmutableVoteEntry.Builder builder() {
    return ImmutableVoteEntry.builder();
  }

  /**
   * The address of the LP who voted.
   *
   * @return An {@link Address}.
   */
  @JsonProperty("Account")
  @JsonAlias("account")
  Address account();

  /**
   * The trading fee that the LP voted for.
   *
   * @return A {@link TradingFee}.
   */
  @JsonProperty("TradingFee")
  @JsonAlias("trading_fee")
  @Value.Default
  default TradingFee tradingFee(){
    return TradingFee.of(UnsignedInteger.ZERO);
  }

  /**
   * The weight of the LP's vote.
   *
   * @return The {@link VoteWeight}.
   */
  @JsonProperty("VoteWeight")
  @JsonAlias("vote_weight")
  VoteWeight voteWeight();

}
