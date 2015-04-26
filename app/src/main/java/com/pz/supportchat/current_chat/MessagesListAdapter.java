package com.pz.supportchat.current_chat;

import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.InternalMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        MessageView messageView;

        if (convertView == null) {
            messageView = (MessageView) mLayoutInflater.inflate(R.layout.message_view, parent, false);
        } else {
            messageView = (MessageView) convertView;
        }

        final InternalMessage message = getItem(position);
        messageView.setData(message);

        return messageView;
    }

    public void updateMessages(final List<InternalMessage> internalMessages) {
        for (final InternalMessage internalMessage : internalMessages) {
            mInternalMessages.add(internalMessage);
        }
    }

    public void updateWithMessage(final InternalMessage internalMessage) {
        mInternalMessages.add(internalMessage);
    }

    public List<InternalMessage> getMessages() {
        return mInternalMessages;
    }
}
