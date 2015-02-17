package com.pz.supportchat.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class XMPPConnectionProvider {

    public final static String SERVER_ADDRESS = "192.168.0.23";
    public final static String SERVER_HOST = "paddy.local";
    public final static int SERVER_PORT = 5222;
    private XMPPTCPConnection mConnection;
    
    public XMPPConnectionProvider() {
        createConnection();
    }

    public XMPPTCPConnection getConnection() {
        if (mConnection == null) {
            createConnection();
        }
        
        return mConnection;
    }
    
    public String getHost() {
        return SERVER_HOST;
    }

    private void createConnection() {
        final XMPPTCPConnectionConfiguration connectionConfiguration = XMPPTCPConnectionConfiguration.builder()
                .setServiceName(SERVER_HOST)
                .setHost(SERVER_ADDRESS)
                .setPort(SERVER_PORT)
                .setCompressionEnabled(false)
                .setDebuggerEnabled(true)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();

        mConnection = new XMPPTCPConnection(connectionConfiguration);
    }
    
    public boolean isConnected() {
        return mConnection.isConnected();
    }
    
    public boolean isAuthenticated() {
        return mConnection.isAuthenticated();
    }
}
