package com.pz.supportchat.contacts_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.PresenceAwareRosterEntry;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ContactsAdapter extends BaseAdapter {

    private List<PresenceAwareRosterEntry> mRosterEntries;
    private final LayoutInflater mLayoutInflater;

    public ContactsAdapter(final Context context, final List<PresenceAwareRosterEntry> rosterEntries) {
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
        View view = convertView;

        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.contact_view, parent, false);
        }
        final TextView textViewUserName = (TextView) view.findViewById(R.id.textViewUserName);
        final ImageView imageViewStatus = (ImageView) view.findViewById(R.id.imageViewStatus);

        textViewUserName.setText(mRosterEntries.get(position).getName());
        imageViewStatus.setBackgroundColor(resolvePresence(mRosterEntries.get(position).isPresent(), parent.getContext()));

        return view;
    }

    private int resolvePresence(final boolean isPresent, final Context context) {
        if (isPresent) {
            return context.getResources().getColor(R.color.green);
        } else {
            return context.getResources().getColor(R.color.red);
        }
    }

    public void switchContactAvailability(final String user, boolean available) {

        for (final PresenceAwareRosterEntry rosterEntry : mRosterEntries) {
            if (StringUtils.equals(rosterEntry.getUser(), user)) {
                mRosterEntries.get(mRosterEntries.indexOf(rosterEntry)).setPresence(available);
            }
        }
    }
}
