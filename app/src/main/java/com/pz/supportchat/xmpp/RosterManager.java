package com.pz.supportchat.xmpp;

import java.util.Collection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public class RosterManager {

    private PostingRosterListener mPostingRosterListener;

    public RosterManager(final PostingRosterListener postingRosterListener) {
        mPostingRosterListener = postingRosterListener;
    }

    public Collection<RosterEntry> getRosterEntries(final XMPPTCPConnection connection) {
        final Roster roster = Roster.getInstanceFor(connection);
        roster.addRosterListener(mPostingRosterListener);
        return roster.getEntries();
    }

    public boolean addRosterEntry(final String user, final XMPPConnection connection) {
        final Roster roster = Roster.getInstanceFor(connection);
        roster.addRosterListener(mPostingRosterListener);
        try {
            roster.createEntry(user, user, null);
            return true;
        } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
