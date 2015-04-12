package com.pz.supportchat.xmpp;

import android.util.Log;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.bus_events.EntriesUpdated;
import com.pz.supportchat.bus_events.NewEntriesAdded;
import com.pz.supportchat.bus_events.PresenceChangedEvent;
import java.util.Collection;
import javax.inject.Inject;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterListener;

public class PostingRosterListener implements RosterListener {

    @Inject
    protected MainThreadBus mBus;

    @Override
    public void entriesAdded(Collection<String> addresses) {
        mBus.post(new NewEntriesAdded(addresses));

    }

    @Override
    public void entriesUpdated(Collection<String> addresses) {
        mBus.post(new EntriesUpdated(addresses));
    }

    @Override
    public void entriesDeleted(Collection<String> addresses) {
        // nop
    }

    @Override
    public void presenceChanged(Presence presence) {
        Log.d("PRESENCE_DEBUG", "posting change on the bus: " + presence.getMode() + presence.getStatus());
        mBus.post(new PresenceChangedEvent(presence));
    }
}
