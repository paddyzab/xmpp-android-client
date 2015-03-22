package com.pz.supportchat.xmpp;

import org.jivesoftware.smack.ConnectionListener;

public interface IChatManager {

    void connect(final ConnectionListener connectionListener);

    void login(final String user, final String password);

    void disconnect();

    void sendMessage(String message, String currentUser);
}
