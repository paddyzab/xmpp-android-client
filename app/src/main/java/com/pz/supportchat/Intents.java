package com.pz.supportchat;

import android.app.Activity;
import android.content.Intent;
import com.pz.supportchat.contacts_list.ContactsActivity;
import com.pz.supportchat.current_chat.ChatActivity;
import com.pz.supportchat.xmpp.ChatService;

final public class Intents {

    public static final String NICKNAME_KEY = "_nickname";

    public Intent getCurrentChatIntent(final Activity from, final String nickname) {
        final Intent intent = new Intent(from, ChatActivity.class);
        intent.putExtra(NICKNAME_KEY, nickname);

        return intent;
    }

    public Intent getContactsIntent(final Activity from) {
        return new Intent(from, ContactsActivity.class);
    }

    public Intent getChatServiceIntent(final Activity from) {
        final Intent intent = new Intent(from, ChatService.class);
        intent.setAction(ChatService.CHAT_SERVICE_ACTION_START);

        return intent;
    }
}
