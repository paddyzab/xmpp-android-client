package com.pz.supportchat;

import com.pz.supportchat.bus_events.NewMessageEvent;
import com.pz.supportchat.commons.models.Contact;
import com.pz.supportchat.commons.models.InternalMessage;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import android.app.Application;
import android.os.SystemClock;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class PostingMessageListener implements ChatMessageListener {

    @Inject
    protected MainThreadBus mBus;

    @Inject
    protected Application mApplication;

    @Override
    public void processMessage(final Chat chat, final Message message) {

        final Realm realm = Realm.getInstance(mApplication);

        Contact contact;

        final RealmQuery<Contact> query = realm.where(Contact.class);
        final RealmResults<Contact> results = query.equalTo("name", chat.getParticipant())
                .findAll();
        if (results.size() > 0) {
            contact = results.get(0);
        } else {
            contact = null;
            // add this contact to the database
        }

        final InternalMessage newInternalMessage = realm.createObject(InternalMessage.class);
        newInternalMessage.setMessage(message.getBody());
        newInternalMessage.setSelf(false);
        newInternalMessage.setTime(SystemClock.currentThreadTimeMillis());
        newInternalMessage.setContact(contact);

        mBus.post(new NewMessageEvent(newInternalMessage));
    }
}
