package com.pz.supportchat;


import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import android.content.Context;

import javax.inject.Inject;

public class PostingConnectionChangeListener implements ConnectionListener {

    private final String LOG_TAG = PostingConnectionChangeListener.class.getSimpleName();
    
    public final static String CONNECTED = "connected";
    public final static String AUTHENTICATED = "authenticated";
    public final static String DISCONNECTED = "disconnected";
    
    @Inject
    protected MainThreadBus mBus;

    public static class XMPPConnectionStatus {
        public final String mStatus;

        public XMPPConnectionStatus(final String status) {
            this.mStatus = status;
        }
    }

    @Override
    public void connected(XMPPConnection connection) {
        mBus.post(new XMPPConnectionStatus(CONNECTED));
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        mBus.post(new XMPPConnectionStatus(AUTHENTICATED));
    }

    @Override
    public void connectionClosed() {
        mBus.post(new XMPPConnectionStatus(DISCONNECTED));
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        mBus.post(new XMPPConnectionStatus(DISCONNECTED));

    }

    @Override
    public void reconnectionSuccessful() {
        mBus.post(new XMPPConnectionStatus(CONNECTED));
    }

    @Override
    public void reconnectingIn(int seconds) {
        // nop
    }

    @Override
    public void reconnectionFailed(Exception e) {
        // nop
    }

    public void inject(Context context) {
        App.get(context).inject(this);
    }
}
