package com.pz.supportchat.bus_events;

import java.util.Collection;

public class NewEntriesAdded {

    public final Collection<String> newEntries;

    public NewEntriesAdded(final Collection<String> newEntries) {
        this.newEntries = newEntries;
    }
}
