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
import org.xrpl.xrpl4j.model.flags.MpTokenIssuanceSetFlags;

import java.util.Optional;

/**
 * Representation of the {@code MPTokenIssuanceSet} transaction.
 */
@Immutable
@JsonSerialize(as = ImmutableMpTokenIssuanceSet.class)
@JsonDeserialize(as = ImmutableMpTokenIssuanceSet.class)
public interface MpTokenIssuanceSet extends Transaction {

  /**
   * Construct a {@code MpTokenIssuanceSet} builder.
   *
   * @return An {@link ImmutableMpTokenIssuanceSet.Builder}.
   */
  static ImmutableMpTokenIssuanceSet.Builder builder() {
    return ImmutableMpTokenIssuanceSet.builder();
  }

  /**
   * A set of {@link MpTokenIssuanceSetFlags}.
   *
   * @return An {@link MpTokenIssuanceSetFlags}.
   */
  @JsonProperty("Flags")
  @Value.Default
  default MpTokenIssuanceSetFlags flags() {
    return MpTokenIssuanceSetFlags.empty();
  }

  /**
   * The {@link MpTokenIssuanceId} of the issuance to update.
   *
   * @return An {@link MpTokenIssuanceId}.
   */
  @JsonProperty("MPTokenIssuanceID")
  MpTokenIssuanceId mpTokenIssuanceId();

  /**
   * An optional XRPL Address of an individual token holder balance to lock/unlock. If omitted, this transaction will
   * apply to all accounts holding MPTs.
   *
   * @return An optionally-present {@link Address}.
   */
  @JsonProperty("Holder")
  Optional<Address> holder();
}

