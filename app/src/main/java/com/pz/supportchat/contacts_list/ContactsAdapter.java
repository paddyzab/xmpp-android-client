package com.pz.supportchat.contacts_list;

import com.pz.supportchat.R;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.roster.RosterEntry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private List<RosterEntry> mRosterEntries;
    private final LayoutInflater mLayoutInflater;

    public ContactsAdapter(final Context context, final List<RosterEntry> rosterEntries) {
        mRosterEntries = rosterEntries;
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mRosterEntries.size();
    }

    @Override
    public RosterEntry getItem(int position) {
        return mRosterEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // TODO create CustomView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.contact_adapter, parent, false);
        }
        final TextView textViewUserName = (TextView) view.findViewById(R.id.textViewUserName);
        final ImageView imageViewStatus = (ImageView) view.findViewById(R.id.imageViewStatus);
        textViewUserName.setText(mRosterEntries.get(position).getName());

        // TODO since RosterEntry does not carry information about the presence, we will need to change it from outside.
        // Or extend the Roster to carry that data.
        Log.d("SMACK", " roster entry: " + mRosterEntries.get(position).toString());

        return view;
    }

    public void switchContactAvailability(final String user) {
        for (final RosterEntry rosterEntry : mRosterEntries) {
            if (StringUtils.equals(rosterEntry.getUser(), user)) {
                
            } else {
                throw new IllegalStateException("User should be in the roster entry");
            }
        }

    }
}
