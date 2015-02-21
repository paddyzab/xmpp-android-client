package com.pz.supportchat.chats_list;

import com.google.common.collect.Lists;

import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.Intents;
import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.Message;

import org.apache.commons.lang3.StringUtils;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

public class ChatsListActivity extends InjectableActivity {

    @Inject
    protected Intents intents;

    @InjectView(R.id.listViewMessages)
    protected ListView listViewMessages;

    @InjectView(R.id.editTextInputMessage)
    protected EditText editTextInputMessage;

    private String nickname;
    private MessagesListAdapter messagesListAdapter;

    @OnClick(R.id.buttonSend)
    protected void sendMessage() {
        if (validateMessage()) {
            sendNewMessage();
        } else {
            Toast.makeText(ChatsListActivity.this, "Add a message", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNewMessage() {
        final Message newMessage = new Message(nickname, editTextInputMessage.getText().toString(),
                true);
        
        messagesListAdapter.updateWithMessage(newMessage);
        messagesListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_chat_list;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nickname = extras.getString(Intents.NICKNAME_KEY);
            Toast.makeText(ChatsListActivity.this, "Welcome: " + nickname, Toast.LENGTH_SHORT)
                    .show();
        }

        messagesListAdapter = new MessagesListAdapter(ChatsListActivity.this,
                Lists.<Message>newArrayList());
        listViewMessages.setAdapter(messagesListAdapter);

        messagesListAdapter.notifyDataSetChanged();
    }

    private boolean validateMessage() {
        return StringUtils.isNotEmpty(editTextInputMessage.getText().toString());
    }
}
