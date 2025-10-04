package org.xrpl.xrpl4j.model.ledger;

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
import com.google.common.primitives.UnsignedInteger;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.flags.MPTokenFlags;
import org.xrpl.xrpl4j.model.flags.NfTokenOfferFlags;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.CurrencyAmount;
import org.xrpl.xrpl4j.model.transactions.NfTokenId;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.math.BigInteger;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableMPTokenObject.class)
@JsonDeserialize(as = ImmutableMPTokenObject.class)
public interface MPTokenObject extends LedgerObject {

    /**
     * Construct a builder for this class.
     *
     * @return An {@link ImmutableMPTokenObject.Builder}.
     */
    static ImmutableMPTokenObject.Builder builder() {
        return ImmutableMPTokenObject.builder();
    }

    /**
     * The type of ledger object. In this case, this is always "MPToken".
     *
     * @return Always {@link LedgerEntryType#MP_TOKEN}.
     */
    @JsonProperty("LedgerEntryType")
    @Value.Derived
    default LedgerEntryType ledgerEntryType() {
        return LedgerEntryType.MP_TOKEN;
    }


    @JsonProperty("Account")
    Address account();

    @JsonProperty("MPTokenIssuanceID")
    String mpTokenIssuanceID();

    @JsonProperty("MPTAmount")
    String mptAmount();

    @JsonProperty("LockedAmount")
    Optional<String> lockedAmount();

    @JsonProperty("Flags")
    @Value.Default
    default MPTokenFlags flags() {
        return MPTokenFlags.UNSET;
    }
}
