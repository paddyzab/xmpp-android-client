package com.pz.supportchat.xmpp;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

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

        MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(connection);
        
        
        return entries;
    }
    
    public RosterEntry getRosterEntryForUser(final String user, final XMPPTCPConnection connection) {
        final Roster roster = Roster.getInstanceFor(connection);
        return roster.getEntry(user);
    }
    
    
}
