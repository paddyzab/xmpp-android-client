package com.pz.supportchat.xmpp;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

public class ConnectionManager implements IChatManager {

    @Override
    public void connect(final XMPPTCPConnection connection,
            final ConnectionListener connectionListener) {

        connection.addConnectionListener(connectionListener);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connection.connect();
                } catch (SmackException | XMPPException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void login(final XMPPTCPConnection connection, final String user,
            final String password) {
        try {
            connection.login(user, password);
        } catch (XMPPException | SmackException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect(final XMPPTCPConnection connection) {
        connection.disconnect();
    }

    @Override
    public void sendMessage(final XMPPTCPConnection connection,
            final String message, final String currentUser, ChatMessageListener messageListener) {

        final ChatManager chatManager = ChatManager.getInstanceFor(connection);
        final Chat chat = chatManager.createChat(getUserJID(resolveUser(currentUser), connection), messageListener);

        try {
            chat.sendMessage(message);
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
