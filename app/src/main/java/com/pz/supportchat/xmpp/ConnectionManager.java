package com.pz.supportchat.xmpp;

import com.google.common.base.Optional;

import com.pz.supportchat.PostingMessageListener;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import android.util.Log;

import java.io.IOException;
import java.util.Collection;

public class ConnectionManager implements IChatManager {

    private final XMPPTCPConnection mXMPPTCPConnection;
    private final ChatManager mChatManager;
    private Optional<Chat> mChatObservable = Optional.absent();
    private final PostingMessageListener mPostingMessageListener;

    public ConnectionManager(final XMPPTCPConnection connection,
            final PostingMessageListener postingMessageListener) {
        mXMPPTCPConnection = connection;
        mPostingMessageListener = postingMessageListener;
        mChatManager = ChatManager.getInstanceFor(mXMPPTCPConnection);

        mChatManager.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                if (!createdLocally) {
                    chat.addMessageListener(postingMessageListener);
                    mChatObservable = Optional.of(chat);
                }
            }
        });

        final Roster roster = Roster.getInstanceFor(connection);
        roster.addRosterListener(new RosterListener() {
            @Override
            public void entriesAdded(Collection<String> addresses) {

            }

            @Override
            public void entriesUpdated(Collection<String> addresses) {

            }

            @Override
            public void entriesDeleted(Collection<String> addresses) {

            }

            @Override
            public void presenceChanged(Presence presence) {
                Log.d(ConnectionManager.class.getSimpleName(), " new presence from: " + presence.getFrom());
                final Presence bestPresence = roster.getPresence(presence.getFrom());
                bestPresence.isAvailable();
                bestPresence.getStatus();
            }
        });
    }

    @Override
    public void connect(final ConnectionListener connectionListener) {

        mXMPPTCPConnection.addConnectionListener(connectionListener);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mXMPPTCPConnection.connect();
                } catch (SmackException | XMPPException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void login(final String user,
            final String password) {
        try {
            mXMPPTCPConnection.login(user, password);
        } catch (XMPPException | SmackException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        mXMPPTCPConnection.disconnect();
    }

    @Override
    public void sendMessage(final String message, final String currentUser) {

        if (!mChatObservable.isPresent()) {
            Chat chat = mChatManager
                    .createChat(getUserJID(resolveUser(currentUser), mXMPPTCPConnection),
                            mPostingMessageListener);
            chat.addMessageListener(mPostingMessageListener);
            mChatObservable = Optional.fromNullable(chat);
        }

        try {
            mChatObservable.get().sendMessage(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    private String resolveUser(final String currentUser) {

        if (StringUtils.equals(currentUser, "paddy")) {
            return "skarbek";
        } else if (StringUtils.equals(currentUser, "skarbek")) {
            return "paddy";
        } else {
            throw new IllegalStateException("User should be one of two available for now");
        }
    }

    private String getUserJID(final String userId, final XMPPTCPConnection connection) {
        return userId + "@" + connection.getServiceName();
    }
}
