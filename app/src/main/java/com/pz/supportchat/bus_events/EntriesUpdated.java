package com.pz.supportchat.bus_events;

import java.util.Collection;

public class EntriesUpdated {

    private final Collection<String> updatedEntries;

    public EntriesUpdated(final Collection<String> updatedEntries) {
        this.updatedEntries = updatedEntries;
    }
}
