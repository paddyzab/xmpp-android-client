package com.pz.supportchat.chats_list;

import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.InternalMessage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MessagesListAdapter extends BaseAdapter {
    
    private final Context context;
    private final List<InternalMessage> mInternalMessages;

    public MessagesListAdapter(final Context context, final List<InternalMessage> internalMessages) {
        this.context = context;
        this.mInternalMessages = internalMessages;
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
        
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (mInternalMessages.get(position).isSelf) {
            convertView = mInflater.inflate(R.layout.message_self,
                null);
        } else {
            convertView = mInflater.inflate(R.layout.message_other,
                null);
        }

        ((TextView)convertView.findViewById(R.id.textViewMessage)).setText(mInternalMessages.get(position).message);
        ((TextView)convertView.findViewById(R.id.textViewMessageFrom)).setText(mInternalMessages.get(position).fromName);
        
        return convertView;
    }
    
    public void updateMessages(final List<InternalMessage> internalMessages) {
        for (final InternalMessage internalMessage : internalMessages) {
            this.mInternalMessages.add(internalMessage);
        }
    }

    public void updateWithMessage(final InternalMessage internalMessage) {
            this.mInternalMessages.add(internalMessage);
    }
}
