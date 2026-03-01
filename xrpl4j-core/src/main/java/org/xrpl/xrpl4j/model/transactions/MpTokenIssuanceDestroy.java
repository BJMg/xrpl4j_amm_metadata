package org.xrpl.xrpl4j.model.transactions;

/*-
 * ========================LICENSE_START=================================
 * xrpl4j :: core
 * %%
 * Copyright (C) 2020 - 2023 XRPL Foundation and its contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;
import org.xrpl.xrpl4j.model.flags.TransactionFlags;

/**
 * Representation of the {@code MPTokenIssuanceDestroy} transaction.
 */
@Immutable
@JsonSerialize(as = ImmutableMpTokenIssuanceDestroy.class)
@JsonDeserialize(as = ImmutableMpTokenIssuanceDestroy.class)
public interface MpTokenIssuanceDestroy extends Transaction {

  /**
   * Construct a {@code MpTokenIssuanceDestroy} builder.
   *
   * @return An {@link ImmutableMpTokenIssuanceDestroy.Builder}.
   */
  static ImmutableMpTokenIssuanceDestroy.Builder builder() {
    return ImmutableMpTokenIssuanceDestroy.builder();
  }

  /**
   * Set of {@link TransactionFlags}s for this {@link MpTokenIssuanceDestroy}, which only allows the
   * {@code tfFullyCanonicalSig} flag, which is deprecated.
   *
   * <p>The value of the flags cannot be set manually, but exists for JSON serialization/deserialization only and for
   * proper signature computation in rippled.
   *
   * @return Always {@link TransactionFlags#EMPTY}.
   */
  @JsonProperty("Flags")
  @Value.Default
  default TransactionFlags flags() {
    return TransactionFlags.EMPTY;
  }

  /**
   * The {@link MpTokenIssuanceId} of the issuance to destroy.
   *
   * @return An {@link MpTokenIssuanceId}.
   */
  @JsonProperty("MPTokenIssuanceID")
  MpTokenIssuanceId mpTokenIssuanceId();
}

