package com.pz.supportchat.contacts_list;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.common.collect.Lists;
import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.Intents;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.R;
import com.pz.supportchat.bus_events.PresenceChangedEvent;
import com.pz.supportchat.commons.models.PresenceAwareRosterEntry;
import com.pz.supportchat.utils.StringUtils;
import com.pz.supportchat.xmpp.RosterManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;
import com.squareup.otto.Subscribe;
import java.util.List;
import javax.inject.Inject;
import org.jivesoftware.smack.packet.Presence;

public class ContactsActivity extends InjectableActivity {

    @InjectView(R.id.contactsListView)
    public ListView contactsListView;

    @Inject
    protected RosterManager mRosterManager;

    @Inject
    protected XMPPConnectionProvider mXMPPConnectionProvider;

    @Inject
    protected MainThreadBus mBus;

    @Inject
    protected Intents mIntents;

    private ContactsAdapter contactsAdapter;

    @Override
    public int getLayoutResource() {
        return R.layout.contacts_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<PresenceAwareRosterEntry> rosterEntries = Lists.newArrayList(mRosterManager
                .getRosterEntries(mXMPPConnectionProvider.getConnection()));
        contactsAdapter = new ContactsAdapter(ContactsActivity.this, rosterEntries);
        contactsListView.setAdapter(contactsAdapter);

        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(mIntents.getCurrentChatIntent(ContactsActivity.this, contactsAdapter.getItem(position).getName()));
            }
        });

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

        contactsAdapter.switchContactAvailability(StringUtils.parseBareAddress(presence.getFrom()), presence.isAvailable());
        contactsAdapter.notifyDataSetInvalidated();
    }

    // TODO add some basic dialog to set user data
    @OnClick(R.id.buttonAddContact)
    protected void addContact() {
        if (mRosterManager.addRosterEntry("skarbek" + "@" + XMPPConnectionProvider.SERVER_HOST, mXMPPConnectionProvider.getConnection())) {
            Toast.makeText(ContactsActivity.this, "Roster Entry created.", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(ContactsActivity.this, "Unable to create roster. Try again later.", Toast.LENGTH_LONG).show();
        }
    }
}
