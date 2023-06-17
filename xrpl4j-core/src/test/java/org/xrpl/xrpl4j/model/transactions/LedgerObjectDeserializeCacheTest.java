package org.xrpl.xrpl4j.model.transactions;

import org.junit.jupiter.api.Test;
import org.xrpl.xrpl4j.model.ledger.LedgerObject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LedgerObjectDeserializeCacheTest {

    @Test
    void canBeDeserialized() {
        assertThat(LedgerObjectDeserializeCache.getDefault().canBeDeserialized(LedgerObject.LedgerEntryType.RIPPLE_STATE))
                .isTrue();
    }

    @Test
    void cannotBeDeserialized() {
        assertThat(LedgerObjectDeserializeCache.getDefault().canBeDeserialized(LedgerObject.LedgerEntryType.DIRECTORY_NODE))
                .isFalse();
    }
}