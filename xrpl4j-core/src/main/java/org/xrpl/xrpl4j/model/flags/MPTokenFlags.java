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
 * A set of static {@link Flags} which can be set on {@link org.xrpl.xrpl4j.model.ledger.MPTokenObject}s.
 */
public class MPTokenFlags extends Flags {

    /**
     * Constant for an unset flag.
     */
    public static final MPTokenFlags UNSET = new MPTokenFlags(0);

    /**
     * Constant {@link MPTokenFlags} for the {@code lsfMPTLocked} flag.
     */
    public static final MPTokenFlags LOCKED = new MPTokenFlags(0x00000001);

    /**
     * Constant {@link MPTokenFlags} for the {@code lsfMPTAuthorized} flag.
     */
    public static final MPTokenFlags AUTHORIZED = new MPTokenFlags(0x00000002);

    /**
     * Required-args Constructor.
     *
     * @param value The long-number encoded flags value of this {@link MPTokenFlags}.
     */
    private MPTokenFlags(long value) {
        super(value);
    }

    /**
     * Construct {@link MPTokenFlags} with a given value.
     *
     * @param value The long-number encoded flags value of this {@link MPTokenFlags}.
     * @return New {@link MPTokenFlags}.
     */
    public static MPTokenFlags of(long value) {
        return new MPTokenFlags(value);
    }

    /**
     * If set, indicates that all balances are locked.
     *
     * @return {@code true} if {@code lsfMPTLocked} is set, otherwise {@code false}.
     */
    public boolean lsfMptLocked() {
        return this.isSet(LOCKED);
    }

    /**
     * If set, indicates that the issuer has authorized the holder for the MPT.
     *
     * @return {@code true} if {@code lsfMPTAuthorized} is set, otherwise {@code false}.
     */
    public boolean lsfMptAuthorized() {
        return this.isSet(AUTHORIZED);
    }
}
