package com.pz.supportchat.xmpp;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

public class ChatManager implements IChatManager {

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
    public void sendMessage(final String message) {

    }
}
