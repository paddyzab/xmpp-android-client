package com.pz.supportchat.xmpp;

import com.google.common.base.Optional;

import com.pz.supportchat.PostingMessageListener;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

import static org.jivesoftware.smack.packet.Presence.Type.available;

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
            mXMPPTCPConnection.sendPacket(new Presence(available));
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
            final Chat chat = mChatManager
                    .createChat(currentUser + "@" + XMPPConnectionProvider.SERVER_HOST,
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
}
