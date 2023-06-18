package org.xrpl.xrpl4j.model.ledger;

/*-
 * ========================LICENSE_START=================================
 * xrpl4j :: model
 * %%
 * Copyright (C) 2020 - 2022 XRPL Foundation and its contributors
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Strings;
import com.google.common.primitives.UnsignedInteger;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.xrpl.xrpl4j.model.AbstractJsonTest;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Hash256;

class TicketObjectJsonTests extends AbstractJsonTest {

  @Test
  void testJson() throws JSONException, JsonProcessingException {
    TicketObject ticketObject = TicketObject.builder()
      .account(Address.of("rEhxGqkqPPSxQ3P25J66ft5TwpzV14k2de"))
      .ownerNode("0000000000000000")
      .previousTransactionId(Hash256.of("F19AD4577212D3BEACA0F75FE1BA1644F2E854D46E8D62E9C95D18E9708CBFB1"))
      .previousTransactionLedgerSequence(UnsignedInteger.valueOf(4))
      .ticketSequence(UnsignedInteger.valueOf(3))
      .index(Hash256.of(Strings.repeat("0", 64)))
      .build();

    String json = "{\n" +
      "  \"Account\" : \"rEhxGqkqPPSxQ3P25J66ft5TwpzV14k2de\",\n" +
      "  \"LedgerEntryType\" : \"Ticket\",\n" +
      "  \"OwnerNode\" : \"0000000000000000\",\n" +
      "  \"PreviousTxnID\" : \"F19AD4577212D3BEACA0F75FE1BA1644F2E854D46E8D62E9C95D18E9708CBFB1\",\n" +
      "  \"PreviousTxnLgrSeq\" : 4,\n" +
      "  \"TicketSequence\" : 3,\n" +
      "  \"index\" : " + objectMapper.writeValueAsString(Strings.repeat("0", 64)) +
      "}";

    assertCanSerializeAndDeserialize(ticketObject, json);
  }
}
