package com.pz.supportchat.chats_list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.Message;
import java.util.List;

public class MessagesListAdapter extends BaseAdapter {
    
    private final Context context;
    private final List<Message> messages;

    public MessagesListAdapter(final Context context, final List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }
    
    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(final int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (messages.get(position).isSelf) {
            convertView = mInflater.inflate(R.layout.message_self,
                null);
        } else {
            convertView = mInflater.inflate(R.layout.message_other,
                null);
        }

        ((TextView)convertView.findViewById(R.id.textViewMessage)).setText(messages.get(position).message);
        ((TextView)convertView.findViewById(R.id.textViewMessageFrom)).setText(messages.get(position).fromName);
        
        return convertView;
    }
    
    public void updateMessages(final List<Message> messages) {
        for (final Message message : messages) {
            this.messages.add(message);
        }
    }

    public void updateWithMessage(final Message message) {
            this.messages.add(message);
    }
}
