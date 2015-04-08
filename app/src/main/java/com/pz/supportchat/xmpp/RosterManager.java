package com.pz.supportchat.xmpp;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import android.util.Log;

import java.util.Collection;
import java.util.Set;

public class RosterManager {

    public Collection<RosterEntry> getRosterEntries(final XMPPTCPConnection connection) {
        final Roster roster = Roster.getInstanceFor(connection);
        final Set<RosterEntry> entries = roster.getEntries();

        for (final RosterEntry entry : entries) {
            Log.d("SMACK", "Entry: " + entry.getName() + " " + entry.getStatus());
        }

        final Collection<RosterGroup> groups = roster.getGroups();
        for (RosterGroup group : groups) {
            Log.d("SMACK", "group: " + group.getName() + " " + group.getEntries());
        }

        return entries;
    }

    public RosterEntry getRosterEntryForUser(final String user,
            final XMPPTCPConnection connection) {
        final Roster roster = Roster.getInstanceFor(connection);
        return roster.getEntry(user);
    }

    public boolean createRosterEntry(final String user, final XMPPConnection connection) {
        final Roster roster = Roster.getInstanceFor(connection);
        try {
            roster.createEntry(user, user, null);
            return true;
        } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
