package com.pz.supportchat.commons.models;

public class InternalMessage {
    
    public final String fromName;
    public final String message;
    public final boolean isSelf;

    public InternalMessage(final String fromName, final String message, final boolean isSelf) {
        this.fromName = fromName;
        this.message = message;
        this.isSelf = isSelf;
    }
}
