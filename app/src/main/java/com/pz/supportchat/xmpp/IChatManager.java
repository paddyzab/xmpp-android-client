package com.pz.supportchat.xmpp;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public interface IChatManager {

    void connect(final XMPPTCPConnection connection,
            final ConnectionListener connectionListener);

    void login(final XMPPTCPConnection mConnection, final String user, final String password);

    void disconnect(final XMPPTCPConnection connection);

    void sendMessage(XMPPTCPConnection connection,
            String message, String currentUser, ChatMessageListener messageListener);
}
