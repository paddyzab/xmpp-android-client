package com.pz.supportchat;

import com.pz.supportchat.bus_events.NewMessageEvent;
import com.pz.supportchat.commons.models.InternalMessage;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import android.app.Application;
import android.os.SystemClock;

import javax.inject.Inject;

public class PostingMessageListener implements ChatMessageListener {

    @Inject
    protected MainThreadBus mBus;

    @Inject
    protected Application mApplication;

    @Override
    public void processMessage(final Chat chat, final Message message) {

        final InternalMessage newInternalMessage = new InternalMessage();
        newInternalMessage.setMessage(message.getBody());
        newInternalMessage.setSelf(false);
        newInternalMessage.setTime(SystemClock.currentThreadTimeMillis());

        mBus.post(new NewMessageEvent(newInternalMessage));
    }
}
