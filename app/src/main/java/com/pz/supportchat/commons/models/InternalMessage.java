package com.pz.supportchat.commons.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class InternalMessage extends RealmObject {

    @Index
    private String message;
    private Contact contact;
    private boolean isSelf;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }
}
