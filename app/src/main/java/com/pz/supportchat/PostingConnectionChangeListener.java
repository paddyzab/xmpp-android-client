package com.pz.supportchat;


import com.squareup.otto.Bus;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

public class PostingConnectionChangeListener implements ConnectionListener {

    private final String LOG_TAG = PostingConnectionChangeListener.class.getSimpleName();
    
    public final static String CONNECTED = "connected";
    public final static String AUTHENTICATED = "authenticated";
    public final static String DISCONNECTED = "disconnected";
    
    @Inject
    protected Bus mBus;

    public static class XMPPConnectionStatus {
        public final String mStatus;

        public XMPPConnectionStatus(final String status) {
            this.mStatus = status;
        }
    }

    @Override
    public void connected(XMPPConnection connection) {
        Log.d("SMACK", "BUS -- > new connection: " + CONNECTED);
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
