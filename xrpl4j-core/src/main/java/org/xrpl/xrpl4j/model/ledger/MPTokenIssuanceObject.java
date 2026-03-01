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
import com.google.common.primitives.UnsignedLong;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.flags.MpTokenIssuanceFlags;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.AssetScale;
import org.xrpl.xrpl4j.model.transactions.Hash256;
import org.xrpl.xrpl4j.model.transactions.MpTokenIssuanceId;
import org.xrpl.xrpl4j.model.transactions.MpTokenMetadata;
import org.xrpl.xrpl4j.model.transactions.MpTokenNumericAmount;
import org.xrpl.xrpl4j.model.transactions.TransferFee;

import java.util.Optional;

/**
 * Represents an {@code MPTokenIssuance} ledger object.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableMPTokenIssuanceObject.class)
@JsonDeserialize(as = ImmutableMPTokenIssuanceObject.class)
public interface MPTokenIssuanceObject extends LedgerObject {

    /**
     * Construct a builder for this class.
     *
     * @return An {@link ImmutableMPTokenIssuanceObject.Builder}.
     */
    static ImmutableMPTokenIssuanceObject.Builder builder() {
        return ImmutableMPTokenIssuanceObject.builder();
    }

    /**
     * The type of ledger object.
     *
     * @return Always {@link LedgerEntryType#MP_TOKEN_ISSUANCE}.
     */
    @JsonProperty("LedgerEntryType")
    @Value.Derived
    default LedgerEntryType ledgerEntryType() {
        return LedgerEntryType.MP_TOKEN_ISSUANCE;
    }

    /**
     * The {@link MpTokenIssuanceFlags} for this issuance.
     *
     * @return An {@link MpTokenIssuanceFlags}.
     */
    @JsonProperty("Flags")
    @Value.Default
    default MpTokenIssuanceFlags flags() {
        return MpTokenIssuanceFlags.UNSET;
    }

    /**
     * The {@link Address} of the issuer of this token.
     *
     * @return An {@link Address}.
     */
    @JsonProperty("Issuer")
    Optional<Address> issuer();

    /**
     * A 32-bit unsigned integer that is used to ensure issuances from a given sender may only ever exist once.
     *
     * @return An {@link UnsignedInteger} representing the account sequence number.
     */
    @JsonProperty("Sequence")
    Optional<UnsignedInteger> sequence();

    /**
     * The fee that this issuance charges for secondary sales of the token.
     *
     * @return A {@link TransferFee}.
     */
    @JsonProperty("TransferFee")
    @Value.Default
    default TransferFee transferFee() {
        return TransferFee.of(UnsignedInteger.ZERO);
    }

    /**
     * The {@link AssetScale} of the issuance.
     *
     * @return An {@link AssetScale}.
     */
    @JsonProperty("AssetScale")
    @Value.Default
    default AssetScale assetScale() {
        return AssetScale.of(UnsignedInteger.ZERO);
    }

    /**
     * The maximum number of this issuance that can be distributed to non-issuing accounts.
     *
     * @return An optionally present {@link MpTokenNumericAmount}.
     */
    @JsonProperty("MaximumAmount")
    Optional<MpTokenNumericAmount> maximumAmount();

    /**
     * The sum of all token amounts that have been minted to all token holders.
     *
     * @return An {@link MpTokenNumericAmount}.
     */
    @JsonProperty("OutstandingAmount")
    @Value.Default
    default MpTokenNumericAmount outstandingAmount() {
        return MpTokenNumericAmount.of(UnsignedLong.ZERO);
    }

    /**
     * The total amount of this MPT that is locked in escrows across all holders.
     *
     * @return An optionally-present {@link MpTokenNumericAmount}.
     */
    @JsonProperty("LockedAmount")
    Optional<MpTokenNumericAmount> lockedAmount();

    /**
     * Arbitrary hex-encoded metadata about this issuance.
     *
     * @return An optionally-present {@link MpTokenMetadata}.
     */
    @JsonProperty("MPTokenMetadata")
    Optional<MpTokenMetadata> mpTokenMetadata();

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

    /**
     * The {@link MpTokenIssuanceId} of the issuance. Only present in responses to {@code ledger_data} and
     * {@code account_objects} RPC calls.
     *
     * @return An {@link Optional} {@link MpTokenIssuanceId}.
     */
    @JsonProperty("mpt_issuance_id")
    Optional<MpTokenIssuanceId> mpTokenIssuanceId();
}
