package com.pz.supportchat.xmpp;

import com.google.common.collect.Lists;
import com.pz.supportchat.commons.models.PresenceAwareRosterEntry;
import java.util.Collection;
import java.util.List;
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

    public Collection<PresenceAwareRosterEntry> getRosterEntries(final XMPPTCPConnection connection) {
        final Roster roster = Roster.getInstanceFor(connection);
        final List<PresenceAwareRosterEntry> presenceAwareRosterEntries = Lists.newArrayList();
        roster.addRosterListener(mPostingRosterListener);

        for (RosterEntry rosterEntry : roster.getEntries()) {
            presenceAwareRosterEntries.add(new PresenceAwareRosterEntry(rosterEntry));
        }

        return presenceAwareRosterEntries;
    }

    public boolean addRosterEntry(final String user, final XMPPConnection connection) {
        final Roster roster = Roster.getInstanceFor(connection);
        roster.addRosterListener(mPostingRosterListener);
        try {
            roster.createEntry(createJID(user), user, null);
            return true;
        } catch (SmackException.NotLoggedInException | SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String createJID(final String user) {
        return user + "@" + XMPPConnectionProvider.SERVER_HOST;
    }
}
