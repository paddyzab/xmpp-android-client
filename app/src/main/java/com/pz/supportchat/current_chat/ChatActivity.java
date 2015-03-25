package com.pz.supportchat.current_chat;

import com.google.common.collect.Lists;

import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.Intents;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.R;
import com.pz.supportchat.bus_events.NewMessageEvent;
import com.pz.supportchat.commons.models.InternalMessage;
import com.pz.supportchat.xmpp.ConnectionManager;
import com.squareup.otto.Subscribe;

import org.apache.commons.lang3.StringUtils;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

public class ChatActivity extends InjectableActivity {

    private final static String EMPTY_STRING = "";

    @Inject
    protected MainThreadBus mBus;

    @Inject
    protected ConnectionManager mConnectionManager;

    @InjectView(R.id.listViewMessages)
    protected ListView listViewMessages;

    @InjectView(R.id.editTextInputMessage)
    protected EditText editTextInputMessage;

    private String currentUser;
    private MessagesListAdapter messagesListAdapter;

    @OnClick(R.id.buttonSend)
    protected void sendMessage() {
        if (validateMessage()) {
            sendNewMessage();
            editTextInputMessage.setText(EMPTY_STRING);
        } else {
            Toast.makeText(ChatActivity.this, "Add a message", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNewMessage() {
        final InternalMessage newInternalMessage = new InternalMessage(currentUser,
                editTextInputMessage.getText().toString(),
                true);

        mConnectionManager.sendMessage(editTextInputMessage.getText().toString(),
                currentUser);

        messagesListAdapter.updateWithMessage(newInternalMessage);
        messagesListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.current_chat;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUser = extras.getString(Intents.NICKNAME_KEY);
            Toast.makeText(ChatActivity.this, "Welcome: " + currentUser, Toast.LENGTH_SHORT)
                    .show();
        }

        messagesListAdapter = new MessagesListAdapter(ChatActivity.this,
                Lists.<InternalMessage>newArrayList());
        listViewMessages.setAdapter(messagesListAdapter);

        messagesListAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onNewMessageReceived(final NewMessageEvent newMessageEvent) {
        messagesListAdapter.updateWithMessage(newMessageEvent.mInternalMessage);
        messagesListAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    private boolean validateMessage() {
        return StringUtils.isNotEmpty(editTextInputMessage.getText().toString());
    }
}
