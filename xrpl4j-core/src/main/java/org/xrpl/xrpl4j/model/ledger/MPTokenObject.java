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
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Hash256;
import org.xrpl.xrpl4j.model.transactions.MpTokenIssuanceId;
import org.xrpl.xrpl4j.model.transactions.MpTokenNumericAmount;

import java.util.Optional;

/**
 * Represents an {@code MPToken} ledger object.
 */
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

    /**
     * The {@link MPTokenFlags} for this token.
     *
     * @return An {@link MPTokenFlags}.
     */
    @JsonProperty("Flags")
    @Value.Default
    default MPTokenFlags flags() {
        return MPTokenFlags.UNSET;
    }

    /**
     * The {@link Address} of the owner of this MPToken.
     *
     * @return An {@link Address}.
     */
    @JsonProperty("Account")
    Optional<Address> account();

    /**
     * The {@link MpTokenIssuanceId} of the MPTokenIssuance that this token corresponds to.
     *
     * @return An {@link MpTokenIssuanceId}.
     */
    @JsonProperty("MPTokenIssuanceID")
    Optional<MpTokenIssuanceId> mpTokenIssuanceId();

    /**
     * The balance of this MPToken. Defaults to 0.
     *
     * @return An {@link MpTokenNumericAmount}.
     */
    @JsonProperty("MPTAmount")
    @Value.Default
    default MpTokenNumericAmount mptAmount() {
        return MpTokenNumericAmount.of(0);
    }

    /**
     * The amount of this MPToken that is locked in escrows.
     *
     * @return An optionally-present {@link MpTokenNumericAmount}.
     */
    @JsonProperty("LockedAmount")
    Optional<MpTokenNumericAmount> lockedAmount();

    /**
     * The identifying hash of the transaction that most recently modified this object.
     *
     * @return An optionally-present {@link Hash256} containing the previous transaction hash.
     */
    @JsonProperty("PreviousTxnID")
    Optional<Hash256> previousTransactionId();

    /**
     * The index of the ledger that contains the transaction that most recently modified this object.
     *
     * @return An optionally-present {@link UnsignedInteger} representing the previous transaction ledger sequence.
     */
    @JsonProperty("PreviousTxnLgrSeq")
    Optional<UnsignedInteger> previousTransactionLedgerSequence();

    /**
     * A hint indicating which page of the owner directory links to this object.
     *
     * @return An {@link Optional} of type {@link String} containing the owner node hint.
     */
    @JsonProperty("OwnerNode")
    Optional<String> ownerNode();
}
