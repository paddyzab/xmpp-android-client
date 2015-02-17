package com.pz.supportchat.xmpp;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public interface IChatManager {

    void connect(final XMPPTCPConnection connection,
            final ConnectionListener connectionListener);

    void login(final XMPPTCPConnection mConnection, final String user, final String password);

    void disconnect(final XMPPTCPConnection connection);

    public void sendMessage(final String message);
}
