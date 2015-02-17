package com.pz.supportchat.commons.models;

import java.util.List;

public class Messages {
    private List<Message> messages;

    public Messages(List<Message> messages) {
        this.messages = messages;
    }

    public Message get(int position) {
        return messages.get(position);
    }

    public int size() {
        return messages.size();
    }
}
