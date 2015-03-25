package com.pz.supportchat;

import com.pz.supportchat.bus_events.NewMessageEvent;
import com.pz.supportchat.commons.models.InternalMessage;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

import javax.inject.Inject;

public class PostingMessageListener implements ChatMessageListener {

    @Inject
    protected MainThreadBus mBus;

    @Override
    public void processMessage(final Chat chat, final Message message) {
        final InternalMessage newInternalMessage = new InternalMessage(chat.getParticipant(),
                message.getBody(),
                false);

        mBus.post(new NewMessageEvent(newInternalMessage));
    }
}
