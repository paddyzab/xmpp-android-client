package com.pz.supportchat.contacts_list;

import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.PresenceAwareRosterEntry;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private List<PresenceAwareRosterEntry> mRosterEntries;
    private final LayoutInflater mLayoutInflater;

    public ContactsAdapter(final Context context,
            final List<PresenceAwareRosterEntry> rosterEntries) {
        mRosterEntries = rosterEntries;
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mRosterEntries.size();
    }

    @Override
    public PresenceAwareRosterEntry getItem(int position) {
        return mRosterEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactView view;

        if (convertView == null) {
            view = (ContactView) mLayoutInflater
                    .inflate(R.layout.contact_view, null);
        } else {
            view = (ContactView) convertView;
        }

        final PresenceAwareRosterEntry rosterEntry = getItem(position);
        view.setData(rosterEntry, parent.getContext());

        return view;
    }

    public void switchContactAvailability(final String user, boolean available) {

        for (final PresenceAwareRosterEntry rosterEntry : mRosterEntries) {
            if (StringUtils.equals(rosterEntry.getUser(), user)) {
                mRosterEntries.get(mRosterEntries.indexOf(rosterEntry)).setPresence(available);
            }
        }
    }
}
