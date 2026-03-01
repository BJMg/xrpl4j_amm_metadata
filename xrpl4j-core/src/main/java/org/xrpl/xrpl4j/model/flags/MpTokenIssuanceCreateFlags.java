package org.xrpl.xrpl4j.model.flags;

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

/**
 * A set of static {@link TransactionFlags} which can be set on
 * {@link org.xrpl.xrpl4j.model.transactions.MpTokenIssuanceCreate} transactions.
 */
@SuppressWarnings("abbreviationaswordinname")
public class MpTokenIssuanceCreateFlags extends TransactionFlags {

  /**
   * Constant {@link MpTokenIssuanceCreateFlags} for the {@code tfMPTCanLock} flag.
   */
  protected static final MpTokenIssuanceCreateFlags CAN_LOCK = new MpTokenIssuanceCreateFlags(0x00000002);
  /**
   * Constant {@link MpTokenIssuanceCreateFlags} for the {@code tfMPTRequireAuth} flag.
   */
  protected static final MpTokenIssuanceCreateFlags REQUIRE_AUTH = new MpTokenIssuanceCreateFlags(0x00000004);
  /**
   * Constant {@link MpTokenIssuanceCreateFlags} for the {@code tfMPTCanEscrow} flag.
   */
  protected static final MpTokenIssuanceCreateFlags CAN_ESCROW = new MpTokenIssuanceCreateFlags(0x00000008);
  /**
   * Constant {@link MpTokenIssuanceCreateFlags} for the {@code tfMPTCanTrade} flag.
   */
  protected static final MpTokenIssuanceCreateFlags CAN_TRADE = new MpTokenIssuanceCreateFlags(0x00000010);
  /**
   * Constant {@link MpTokenIssuanceCreateFlags} for the {@code tfMPTCanTransfer} flag.
   */
  protected static final MpTokenIssuanceCreateFlags CAN_TRANSFER = new MpTokenIssuanceCreateFlags(0x00000020);
  /**
   * Constant {@link MpTokenIssuanceCreateFlags} for the {@code tfMPTCanClawback} flag.
   */
  protected static final MpTokenIssuanceCreateFlags CAN_CLAWBACK = new MpTokenIssuanceCreateFlags(0x00000040);

  private MpTokenIssuanceCreateFlags(long value) {
    super(value);
  }

  private MpTokenIssuanceCreateFlags() {
    super(0);
  }

  /**
   * Create a new {@link Builder}.
   *
   * @return A new {@link Builder}.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Construct an empty instance of {@link MpTokenIssuanceCreateFlags}.
   *
   * @return An empty {@link MpTokenIssuanceCreateFlags}.
   */
  public static MpTokenIssuanceCreateFlags empty() {
    return new MpTokenIssuanceCreateFlags();
  }

  /**
   * Construct {@link MpTokenIssuanceCreateFlags} with a given value.
   *
   * @param value The long-number encoded flags value of this {@link MpTokenIssuanceCreateFlags}.
   *
   * @return New {@link MpTokenIssuanceCreateFlags}.
   */
  public static MpTokenIssuanceCreateFlags of(long value) {
    return new MpTokenIssuanceCreateFlags(value);
  }

  private static MpTokenIssuanceCreateFlags of(
    boolean tfFullyCanonicalSig,
    boolean tfMPTCanLock,
    boolean tfMPTRequireAuth,
    boolean tfMPTCanEscrow,
    boolean tfMPTCanTrade,
    boolean tfMPTCanTransfer,
    boolean tfMPTCanClawback
  ) {
    return new MpTokenIssuanceCreateFlags(
      TransactionFlags.of(
        tfFullyCanonicalSig ? TransactionFlags.FULLY_CANONICAL_SIG : UNSET,
        tfMPTCanLock ? CAN_LOCK : UNSET,
        tfMPTRequireAuth ? REQUIRE_AUTH : UNSET,
        tfMPTCanEscrow ? CAN_ESCROW : UNSET,
        tfMPTCanTransfer ? CAN_TRANSFER : UNSET,
        tfMPTCanTrade ? CAN_TRADE : UNSET,
        tfMPTCanClawback ? CAN_CLAWBACK : UNSET
      ).getValue()
    );
  }

  /**
   * If set, indicates that the MPT can be locked both individually and globally.
   *
   * @return {@code true} if {@code tfMPTCanLock} is set, otherwise {@code false}.
   */
  public boolean tfMptCanLock() {
    return this.isSet(CAN_LOCK);
  }

  /**
   * If set, indicates that individual holders must be authorized.
   *
   * @return {@code true} if {@code tfMPTRequireAuth} is set, otherwise {@code false}.
   */
  public boolean tfMptRequireAuth() {
    return this.isSet(REQUIRE_AUTH);
  }

  /**
   * If set, indicates that individual holders can place their balances into an escrow.
   *
   * @return {@code true} if {@code tfMPTCanEscrow} is set, otherwise {@code false}.
   */
  public boolean tfMptCanEscrow() {
    return this.isSet(CAN_ESCROW);
  }

  /**
   * If set, indicates that individual holders can trade their balances using the XRP Ledger DEX.
   *
   * @return {@code true} if {@code tfMPTCanTrade} is set, otherwise {@code false}.
   */
  public boolean tfMptCanTrade() {
    return this.isSet(CAN_TRADE);
  }

  /**
   * If set, indicates that tokens may be transferred by any account.
   *
   * @return {@code true} if {@code tfMPTCanTransfer} is set, otherwise {@code false}.
   */
  public boolean tfMptCanTransfer() {
    return this.isSet(CAN_TRANSFER);
  }

  /**
   * If set, indicates that the issuer may use the Clawback transaction.
   *
   * @return {@code true} if {@code tfMPTCanClawback} is set, otherwise {@code false}.
   */
  public boolean tfMptCanClawback() {
    return this.isSet(CAN_CLAWBACK);
  }

  /**
   * A builder class for {@link MpTokenIssuanceCreateFlags}.
   */
  public static class Builder {

    private boolean tfMptCanLock = false;
    private boolean tfMptRequireAuth = false;
    private boolean tfMptCanEscrow = false;
    private boolean tfMptCanTrade = false;
    private boolean tfMptCanTransfer = false;
    private boolean tfMptCanClawback = false;

    public Builder tfMptCanLock(boolean tfMptCanLock) {
      this.tfMptCanLock = tfMptCanLock;
      return this;
    }

    public Builder tfMptRequireAuth(boolean tfMptRequireAuth) {
      this.tfMptRequireAuth = tfMptRequireAuth;
      return this;
    }

    public Builder tfMptCanEscrow(boolean tfMptCanEscrow) {
      this.tfMptCanEscrow = tfMptCanEscrow;
      return this;
    }

    public Builder tfMptCanTrade(boolean tfMptCanTrade) {
      this.tfMptCanTrade = tfMptCanTrade;
      return this;
    }

    public Builder tfMptCanTransfer(boolean tfMptCanTransfer) {
      this.tfMptCanTransfer = tfMptCanTransfer;
      return this;
    }

    public Builder tfMptCanClawback(boolean tfMptCanClawback) {
      this.tfMptCanClawback = tfMptCanClawback;
      return this;
    }

    public MpTokenIssuanceCreateFlags build() {
      return MpTokenIssuanceCreateFlags.of(
        true,
        tfMptCanLock,
        tfMptRequireAuth,
        tfMptCanEscrow,
        tfMptCanTrade,
        tfMptCanTransfer,
        tfMptCanClawback
      );
    }
  }
}

