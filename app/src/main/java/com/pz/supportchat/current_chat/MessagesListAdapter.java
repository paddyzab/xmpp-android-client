package com.pz.supportchat.current_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.InternalMessage;
import java.util.List;

public class MessagesListAdapter extends BaseAdapter {

    private final List<InternalMessage> mInternalMessages;
    private final LayoutInflater mLayoutInflater;

    public MessagesListAdapter(final Context context, final List<InternalMessage> internalMessages) {
        this.mInternalMessages = internalMessages;

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mInternalMessages.size();
    }

    @Override
    public InternalMessage getItem(final int position) {
        return mInternalMessages.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (mInternalMessages.get(position).isSelf) {
            convertView = mLayoutInflater.inflate(R.layout.message_view_self,
                    null);
        } else {
            convertView = mLayoutInflater.inflate(R.layout.message_view_others,
                    null);
        }

        ((TextView) convertView.findViewById(R.id.textViewMessage)).setText(mInternalMessages.get(position).message);
        ((TextView) convertView.findViewById(R.id.textViewMessageFrom)).setText(mInternalMessages.get(position).fromName);

        return convertView;
    }

    public void updateMessages(final List<InternalMessage> internalMessages) {
        for (final InternalMessage internalMessage : internalMessages) {
            mInternalMessages.add(internalMessage);
        }
    }

    public void updateWithMessage(final InternalMessage internalMessage) {
        mInternalMessages.add(internalMessage);
    }
}
