package com.pz.supportchat.current_chat;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.OnClick;
import com.google.common.collect.Lists;
import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.Intents;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.R;
import com.pz.supportchat.bus_events.NewMessageEvent;
import com.pz.supportchat.commons.models.InternalMessage;
import com.pz.supportchat.storage.SharedPreferencesKeyValueStorage;
import com.pz.supportchat.xmpp.ConnectionManager;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import static com.pz.supportchat.current_chat.ChatDataFragment.*;

public class ChatActivity extends InjectableActivity {

    public final static String EMPTY_STRING = "";

    @Inject
    protected MainThreadBus mBus;

    @Inject
    protected ConnectionManager mConnectionManager;

    @Inject
    protected SharedPreferencesKeyValueStorage mSharedPreferencesKeyValueStorage;

    @Inject
    protected ChatDataFragment chatDataFragment;

    @InjectView(R.id.listViewMessages)
    protected ListView listViewMessages;

    @InjectView(R.id.editTextInputMessage)
    protected EditText editTextInputMessage;
    private String currentChatUser;
    private MessagesListAdapter messagesListAdapter;

    @OnClick(R.id.buttonSend)
    protected void sendMessage() {
        if (validateMessage()) {
            sendNewMessage();
            editTextInputMessage.setText(EMPTY_STRING);
        } else {
            Toast.makeText(ChatActivity.this, "Hey, add a message before", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNewMessage() {
        final InternalMessage newInternalMessage = new InternalMessage(
                mSharedPreferencesKeyValueStorage
                        .getString(mSharedPreferencesKeyValueStorage.LOGIN_KEY),
                editTextInputMessage.getText().toString(),
                true);
        messagesListAdapter.updateWithMessage(newInternalMessage);

        mConnectionManager.sendMessage(editTextInputMessage.getText().toString(),
                currentChatUser);
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
        currentChatUser = extras.getString(Intents.NICKNAME_KEY);
        messagesListAdapter = new MessagesListAdapter(ChatActivity.this,
                Lists.<InternalMessage>newArrayList());
        listViewMessages.setAdapter(messagesListAdapter);

        messagesListAdapter.notifyDataSetChanged();

        final FragmentManager fragmentManager = getFragmentManager();
        chatDataFragment = (ChatDataFragment) fragmentManager.findFragmentByTag(CHAT_DATA_FRAGMENT_KEY);

        if (chatDataFragment == null) {
            chatDataFragment = new ChatDataFragment();
            fragmentManager.beginTransaction().add(chatDataFragment, CHAT_DATA_FRAGMENT_KEY).commit();
            chatDataFragment.setData(new ChatViewModel(messagesListAdapter.getMessages()));
        } else {
            messagesListAdapter.updateMessages(chatDataFragment.getData().mMessageList);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        chatDataFragment.setData(new ChatViewModel(messagesListAdapter.getMessages()));
    }

    private boolean validateMessage() {
        return StringUtils.isNotEmpty(editTextInputMessage.getText().toString());
    }
}
