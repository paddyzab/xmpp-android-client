package com.pz.supportchat.chats_list;

import com.pz.supportchat.InjectableActivity;
import com.pz.supportchat.Intents;
import com.pz.supportchat.R;
import com.pz.supportchat.commons.models.Message;

import org.apache.commons.lang3.StringUtils;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();
        
        final Message newMessage = new Message(nickname, editTextInputMessage.getText().toString(), isEven(random.nextInt(100)));
        messagesListAdapter.updateWithMessage(newMessage);
        messagesListAdapter.notifyDataSetChanged();
    }
    
    private boolean isEven(int random) {
        return (random % 2) == 0;
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
            Toast.makeText(ChatsListActivity.this, "Welcome: " + nickname, Toast.LENGTH_SHORT).show();
        }

        messagesListAdapter = new MessagesListAdapter(ChatsListActivity.this,
                mockListOfMessages());
        listViewMessages.setAdapter(messagesListAdapter);
        
        messagesListAdapter.notifyDataSetChanged();
    }

    private List<Message> mockListOfMessages() {
        List<Message> messages = new ArrayList<>();
        
        Message message1 = new Message("Bob", "Hey Alice!", false);
        Message message2 = new Message("Alice", "Hey Bob!", true);
        Message message3 = new Message("Bob", "How is your day?", false);
        
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
                
        return messages;
    }

    private boolean validateMessage() {
        return StringUtils.isNotEmpty(editTextInputMessage.getText().toString());
    }
}
