package com.pz.supportchat;

import com.pz.supportchat.contacts_list.ContactsActivity;
import com.pz.supportchat.current_chat.ChatActivity;
import com.pz.supportchat.login_to_chat.LoginToChatActivity;
import com.pz.supportchat.notifications.NotificationService;
import com.pz.supportchat.xmpp.ChatService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

final public class Intents {

    public static final String NICKNAME_KEY = "_nickname";

    public Intent getCurrentChatIntent(final Context from, final String nickname) {
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

    public Intent getNotificationService(final Activity from) {
        return new Intent(from, NotificationService.class);
    }

    public Intent getLoginIntent(final Activity from) {
        final Intent intent = new Intent(from, LoginToChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return intent;
    }
}
