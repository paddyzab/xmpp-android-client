package com.pz.supportchat.xmpp;

import com.pz.supportchat.bus_events.NewMessageEvent;
import com.pz.supportchat.commons.models.InternalMessage;
import com.squareup.otto.Bus;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

import javax.inject.Inject;

public class PostingMessageListener implements ChatMessageListener {

    @Inject
    protected Bus mBus;

    @Override
    public void processMessage(Chat chat, Message message) {
        final InternalMessage newInternalMessage = new InternalMessage(chat.getParticipant(),
                message.getBody(),
                false);
        mBus.post(new NewMessageEvent(newInternalMessage));

        Log.d("DEBUG_CHAT", " <----- " + message.getBody() + " for: " + chat.getParticipant());
    }
}
