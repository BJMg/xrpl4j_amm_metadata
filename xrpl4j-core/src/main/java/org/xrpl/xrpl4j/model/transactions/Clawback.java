package org.xrpl.xrpl4j.model.transactions;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedInteger;
import com.ripple.cryptoconditions.Condition;
import com.ripple.cryptoconditions.Fulfillment;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.flags.TransactionFlags;
import org.xrpl.xrpl4j.model.immutables.FluentCompareTo;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableClawback.class)
@JsonDeserialize(as = ImmutableClawback.class)
public interface Clawback extends Transaction {

  static ImmutableClawback.Builder builder() {
    return ImmutableClawback.builder();
  }

  @JsonProperty("Flags")
  @Value.Derived
  default TransactionFlags flags() {
    return new TransactionFlags.Builder().build();
  }

  @JsonProperty("Amount")
  CurrencyAmount amount();

}
