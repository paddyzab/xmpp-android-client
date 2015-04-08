package com.pz.supportchat.contacts_list;

import com.pz.supportchat.R;

import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.packet.RosterPacket;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private List<RosterEntry> mRosterEntries;

    public ContactsAdapter(final List<RosterEntry> rosterEntries) {
        mRosterEntries = rosterEntries;
    }

    @Override
    public int getCount() {
        return mRosterEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mRosterEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = View.inflate(parent.getContext(), R.layout.contact_adapter, parent);
        final TextView textViewUserName = (TextView) view.findViewById(R.id.textViewUserName);

        textViewUserName.setText(
                mRosterEntries.get(position).getName() + " user: " + mRosterEntries.get(position)
                        .getUser());
        RosterPacket.ItemStatus status = mRosterEntries.get(position).getStatus();


        return view;
    }
}
