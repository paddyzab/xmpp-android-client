package com.pz.supportchat.bus_events;

import org.jivesoftware.smack.packet.Presence;

public class PresenceChangedEvent {

    public final Presence presence;

    public PresenceChangedEvent(final Presence presence) {
        this.presence = presence;
    }
}
