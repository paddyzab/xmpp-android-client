package com.pz.supportchat;

import com.pz.supportchat.chat.CurrentChatActivity;
import com.pz.supportchat.chats_list.ChatActivity;
import com.pz.supportchat.xmpp.ChatService;

import android.app.Activity;
import android.content.Intent;

final public class Intents {

    public static final String NICKNAME_KEY = "_nickname";
    
    public Intent getCurrentChatIntent(final Activity from) {
        final Intent intent = new Intent(from, CurrentChatActivity.class);
        
        return intent;
    }

    public Intent getChatsListIntent(final Activity from, final String nickname) {
        final Intent intent = new Intent(from, ChatActivity.class);
        intent.putExtra(NICKNAME_KEY, nickname);
        
        return intent;
    }
    
    public Intent getChatServiceIntent(final Activity from) {
        Intent intent = new Intent(from, ChatService.class);
        intent.setAction(ChatService.CHAT_SERVICE_ACTION_START);
    
        return intent;
    } 
}
