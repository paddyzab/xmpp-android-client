package com.pz.supportchat.commons.models;

import java.util.List;

public class Messages {
    private List<InternalMessage> mInternalMessages;

    public Messages(List<InternalMessage> internalMessages) {
        this.mInternalMessages = internalMessages;
    }

    public InternalMessage get(int position) {
        return mInternalMessages.get(position);
    }

    public int size() {
        return mInternalMessages.size();
    }
}
