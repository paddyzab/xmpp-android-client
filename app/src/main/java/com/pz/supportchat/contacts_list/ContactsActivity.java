package com.pz.supportchat.contacts_list;

import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.R;
import com.pz.supportchat.xmpp.RosterManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;

import org.jivesoftware.smack.roster.RosterEntry;

import android.os.Bundle;
import android.widget.ListView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

public class ContactsActivity extends InjectableActivity {

    @InjectView(R.id.contactsListView)
    private ListView contactsListView;

    @Inject
    protected RosterManager mRosterManager;

    @Inject
    protected XMPPConnectionProvider mXMPPConnectionProvider;

    @Override
    public int getLayoutResource() {
        return R.layout.contacts_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Collection<RosterEntry> rosterEntries = mRosterManager
                .getRosterEntries(mXMPPConnectionProvider.getConnection());
        
        if (rosterEntries.isEmpty()) {
            //TODO support empty collection case on the list view
        } else {
            //contactsListView.setAdapter();
        }
    }

    @OnClick(R.id.buttonAddContact)
    protected void addContact() {
        mRosterManager.createRosterEntry("test_user", mXMPPConnectionProvider.getConnection());
    }
}
