package com.pz.supportchat.commons.models;

import org.jivesoftware.smack.roster.RosterEntry;

public class PresenceAwareRosterEntry {

    private final RosterEntry mRosterEntry;
    private boolean isPresent;

    public PresenceAwareRosterEntry(final RosterEntry mRosterEntry) {
        this.mRosterEntry = mRosterEntry;
        isPresent = false;
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

    @Override
    public String toString() {
        return "PresenceAwareRosterEntry{" +
                "mRosterEntry=" + mRosterEntry +
                ", isPresent=" + isPresent +
                '}';
    }
}
