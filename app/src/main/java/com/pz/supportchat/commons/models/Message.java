package com.pz.supportchat.commons.models;

public class Message {
    
    public final String fromName;
    public final String message;
    public final boolean isSelf;

    public Message(final String fromName, final String message, final boolean isSelf) {
        this.fromName = fromName;
        this.message = message;
        this.isSelf = isSelf;
    }
}
