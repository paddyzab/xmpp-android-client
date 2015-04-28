package com.pz.supportchat.commons.models;

import org.jivesoftware.smack.roster.RosterEntry;

public class PresenceMessageAwareRosterEntry {

    public final static String EMPTY_STRING = "";
    
    private final RosterEntry mRosterEntry;
    private boolean isPresent;
    private String mLastMessage;

    public PresenceMessageAwareRosterEntry(final RosterEntry mRosterEntry) {
        this.mRosterEntry = mRosterEntry;
        isPresent = false;
        mLastMessage = EMPTY_STRING;
    }

    public String getName() {
        return mRosterEntry.getName();
    }

    public String getUser() {
        return mRosterEntry.getUser();
    }

    public void setPresence(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setLastMessage(String lastMessage) {
        mLastMessage = lastMessage;
    }
    
    public String getLastMessage() {
        return mLastMessage;
    }
    
    @Override
    public String toString() {
        return "PresenceAwareRosterEntry{" +
                "mRosterEntry=" + mRosterEntry +
                ", isPresent=" + isPresent +
                '}';
    }
}
