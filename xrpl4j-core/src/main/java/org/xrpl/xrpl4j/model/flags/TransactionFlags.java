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

import org.xrpl.xrpl4j.model.transactions.Transaction;

/**
 * A set of static {@link Flags} which could apply to any {@link Transaction}.
 */
public class TransactionFlags extends Flags {

  /**
   * Constant for an unset flag.
   */
  protected static final TransactionFlags UNSET = new TransactionFlags(0);

  /**
   * Constant for empty flags.
   */
  public static final TransactionFlags EMPTY = new TransactionFlags(0);

  /**
   * Corresponds to the {@code tfFullyCanonicalSig} flag.
   */
  protected static final TransactionFlags FULLY_CANONICAL_SIG = new TransactionFlags(0x80000000L);

  TransactionFlags(long value) {
    super(value);
  }

  /**
   * Construct {@link TransactionFlags} from one or more {@link Flags} by performing a bitwise OR on all.
   *
   * @param flag   The first {@link Flags}.
   * @param others Zero or more other {@link Flags} to include.
   *
   * @return A new {@link TransactionFlags}.
   */
  public static TransactionFlags of(Flags flag, Flags... others) {
    return new TransactionFlags(Flags.of(flag, others).getValue());
  }

  /**
   * Flags indicating that a fully-canonical signature is required. This flag is highly recommended.
   *
   * @return {@code true} if {@code tfFullyCanonicalSig} is set, otherwise {@code false}.
   * @see "https://xrpl.org/transaction-common-fields.html#flags-field"
   */
  public boolean tfFullyCanonicalSig() {
    return this.isSet(TransactionFlags.FULLY_CANONICAL_SIG);
  }

  /**
   * A builder class for {@link TransactionFlags} flags.
   */
  public static class Builder {

    /**
     * Build a {@link TransactionFlags}.
     *
     * @return {@link TransactionFlags}.
     */
    public TransactionFlags build() {
      return new TransactionFlags(
        TransactionFlags.FULLY_CANONICAL_SIG.getValue()
      );
    }
  }
}
