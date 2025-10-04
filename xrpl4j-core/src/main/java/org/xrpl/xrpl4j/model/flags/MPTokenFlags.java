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

import org.xrpl.xrpl4j.model.ledger.NfTokenOfferObject;

public class MPTokenFlags extends Flags {
    public static final MPTokenFlags UNSET = new MPTokenFlags(0);
    public static final MPTokenFlags MPT_LOCKED = new MPTokenFlags(0x00000001);

    public static final MPTokenFlags MPT_AUTHORIZED = new MPTokenFlags(0x00000002);


    private MPTokenFlags(long value) {
        super(value);
    }

    public static MPTokenFlags of(long value) {
        return new MPTokenFlags(value);
    }

    public boolean lsfMPTLocked() {
        return this.isSet(MPT_LOCKED);
    }

    public boolean lsfMPTAuthorized() {
        return this.isSet(MPT_AUTHORIZED);
    }
}
