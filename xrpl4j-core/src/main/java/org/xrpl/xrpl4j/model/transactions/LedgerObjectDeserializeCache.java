package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.xrpl.xrpl4j.model.ledger.LedgerObject;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class LedgerObjectDeserializeCache {
    private final static LedgerObjectDeserializeCache DEFAULT = getDefault();
    private final Set<LedgerObject.LedgerEntryType> availableLedgerEntryType;

    public static LedgerObjectDeserializeCache getDefault() {
        if (DEFAULT != null) {
            return DEFAULT;
        } else {
            JsonSubTypes annotation = LedgerObject.class.getAnnotation(JsonSubTypes.class);
            return new LedgerObjectDeserializeCache(Arrays.stream(annotation.value())
                    .map(it -> LedgerObject.LedgerEntryType.forValue(it.name()))
                    .collect(Collectors.toSet()));
        }
    }

    private LedgerObjectDeserializeCache(Set<LedgerObject.LedgerEntryType> availableLedgerEntryType) {
        this.availableLedgerEntryType = availableLedgerEntryType;
    }

    public boolean canBeDeserialized(LedgerObject.LedgerEntryType ledgerEntryType) {
        return availableLedgerEntryType.contains(ledgerEntryType);
    }
}

