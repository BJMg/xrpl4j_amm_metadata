package org.xrpl.xrpl4j.model.jackson.modules;

/*-
 * ========================LICENSE_START=================================
 * Crypto-Conditions Jackson
 * %%
 * Copyright (C) 2018 Ripple Labs
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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.google.common.io.BaseEncoding;
import com.ripple.cryptoconditions.Condition;
import com.ripple.cryptoconditions.CryptoConditionReader;
import com.ripple.cryptoconditions.der.DerEncodingException;
import com.ripple.cryptoconditions.jackson.Encoding;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;

/**
 * Jackson deserializer for {@link Condition} using configurable encodings.
 */
public class ConditionDeserializer extends StdScalarDeserializer<Condition> {

  private final Encoding encoding;

  /**
   * Required-args Constructor.
   *
   * @param encoding The {@link Encoding} to use for serialization and deserialization of conditions and fulfillments.
   */
  public ConditionDeserializer(Encoding encoding) {
    super(String.class);
    this.encoding = Objects.requireNonNull(encoding, "Encoding must not be null!");
  }

  @Override
  public @Nullable Condition deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException {
    try {
      switch (encoding) {
        case HEX: {
          return CryptoConditionReader.readCondition(
              BaseEncoding.base16().decode(jsonParser.getText().toUpperCase(Locale.US))
          );
        }
        case BASE64:
        case BASE64_WITHOUT_PADDING: {
          return CryptoConditionReader
              .readCondition(Base64.getDecoder().decode(jsonParser.getText()));
        }
        case BASE64URL:
        case BASE64URL_WITHOUT_PADDING: {
          return CryptoConditionReader
              .readCondition(Base64.getUrlDecoder().decode(jsonParser.getText()));
        }
        default: {
          throw new RuntimeException("Unhandled Condition Encoding!");
        }
      }
    } catch (DerEncodingException e) {
      return null;
    }
  }
}
