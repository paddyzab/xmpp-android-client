package com.pz.supportchat.bus_events;

import com.pz.supportchat.commons.models.InternalMessage;

public class NewMessageEvent {

    public final InternalMessage mInternalMessage;

    public NewMessageEvent(final InternalMessage internalMessage) {
        mInternalMessage = internalMessage;
    }
}
