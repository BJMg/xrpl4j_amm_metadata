package org.xrpl.xrpl4j.model.flags;

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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TrustSetFlagsTests extends AbstractFlagsTest {

  public static Stream<Arguments> data() {
    return getBooleanCombinations(5);
  }

  @ParameterizedTest
  @MethodSource("data")
  public void testFlagsConstructionWithIndividualFlags(
    boolean tfSetfAuth,
    boolean tfSetNoRipple,
    boolean tfClearNoRipple,
    boolean tfSetFreeze,
    boolean tfClearFreeze
  ) {
    TrustSetFlags.Builder builder = TrustSetFlags.builder()
      .tfSetfAuth(tfSetfAuth);

    if (tfSetNoRipple) {
      builder.tfSetNoRipple();
    }

    if (tfClearNoRipple) {
      builder.tfClearNoRipple();
    }

    if (tfSetFreeze) {
      builder.tfSetFreeze();
    }

    if (tfClearFreeze) {
      builder.tfClearFreeze();
    }

    TrustSetFlags flags = builder.build();

    long expectedFlags = getExpectedFlags(
      tfSetfAuth,
      tfSetNoRipple,
      tfClearNoRipple,
      tfSetFreeze,
      tfClearFreeze
    );
    assertThat(flags.getValue()).isEqualTo(expectedFlags);
  }

  @ParameterizedTest
  @MethodSource("data")
  public void testDeriveIndividualFlagsFromFlags(
    boolean tfSetfAuth,
    boolean tfSetNoRipple,
    boolean tfClearNoRipple,
    boolean tfSetFreeze,
    boolean tfClearFreeze
  ) {
    long expectedFlags = getExpectedFlags(
      tfSetfAuth,
      tfSetNoRipple,
      tfClearNoRipple,
      tfSetFreeze,
      tfClearFreeze
    );
    TrustSetFlags flags = TrustSetFlags.of(expectedFlags);

    assertThat(flags.getValue()).isEqualTo(expectedFlags);
    assertThat(flags.tfFullyCanonicalSig()).isEqualTo(true);
    assertThat(flags.tfSetfAuth()).isEqualTo(tfSetfAuth);
    assertThat(flags.tfSetNoRipple()).isEqualTo(tfSetNoRipple);
    assertThat(flags.tfClearNoRipple()).isEqualTo(tfClearNoRipple);
    assertThat(flags.tfSetFreeze()).isEqualTo(tfSetFreeze);
    assertThat(flags.tfClearFreeze()).isEqualTo(tfClearFreeze);
  }

  private long getExpectedFlags(
    boolean tfSetfAuth,
    boolean tfSetNoRipple,
    boolean tfClearNoRipple,
    boolean tfSetFreeze,
    boolean tfClearFreeze
  ) {
    return (TrustSetFlags.FULLY_CANONICAL_SIG.getValue()) |
      (tfSetfAuth ? TrustSetFlags.SET_F_AUTH.getValue() : 0L) |
      (tfSetNoRipple ? TrustSetFlags.SET_NO_RIPPLE.getValue() : 0L) |
      (tfClearNoRipple ? TrustSetFlags.CLEAR_NO_RIPPLE.getValue() : 0L) |
      (tfSetFreeze ? TrustSetFlags.SET_FREEZE.getValue() : 0L) |
      (tfClearFreeze ? TrustSetFlags.CLEAR_FREEZE.getValue() : 0L);
  }
}
