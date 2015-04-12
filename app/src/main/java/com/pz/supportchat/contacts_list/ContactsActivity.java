package com.pz.supportchat.contacts_list;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.common.collect.Lists;
import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.R;
import com.pz.supportchat.bus_events.PresenceChangedEvent;
import com.pz.supportchat.xmpp.RosterManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;
import com.squareup.otto.Subscribe;
import java.util.List;
import javax.inject.Inject;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;

public class ContactsActivity extends InjectableActivity {

    @InjectView(R.id.contactsListView)
    public ListView contactsListView;

    @Inject
    protected RosterManager mRosterManager;

    @Inject
    protected XMPPConnectionProvider mXMPPConnectionProvider;

    @Inject
    protected MainThreadBus mBus;

    private ContactsAdapter contactsAdapter;

    @Override
    public int getLayoutResource() {
        return R.layout.contacts_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<RosterEntry> rosterEntries = Lists.newArrayList(mRosterManager
                .getRosterEntries(mXMPPConnectionProvider.getConnection()));
        contactsAdapter = new ContactsAdapter(ContactsActivity.this, rosterEntries);
        contactsListView.setAdapter(contactsAdapter);

        mBus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Subscribe
    public void onPresenceChanged(final PresenceChangedEvent event) {
        final Presence presence = event.presence;

        Toast.makeText(ContactsActivity.this, presence.getFrom() + ", is " + presence.getType(), Toast.LENGTH_LONG).show();
        contactsAdapter.notifyDataSetInvalidated();
    }

    @OnClick(R.id.buttonAddContact)
    protected void addContact() {
        if (mRosterManager.addRosterEntry("skarbek" + "@" + XMPPConnectionProvider.SERVER_HOST, mXMPPConnectionProvider.getConnection())) {
            Toast.makeText(ContactsActivity.this, "Roster Entry created.", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(ContactsActivity.this, "Unable to create roster. Try again later.", Toast.LENGTH_LONG).show();
        }
    }
}
