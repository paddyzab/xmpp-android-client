package com.pz.supportchat.xmpp;

import com.pz.supportchat.App;
import com.pz.supportchat.PostingConnectionChangeListener;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import javax.inject.Inject;

public class ChatService extends Service {

    private final static String LOG_TAG = ChatService.class.getSimpleName();

    public static final String CHAT_SERVICE_ACTION_START
            = "START";
    public static final String CHAT_SERVICE_ACTION_STOP
            = "STOP";

    @Inject
    protected ConnectionManager mConnectionManager;

    @Inject
    protected PostingConnectionChangeListener mPostingConnectionChangeListener;
    
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action.equals(CHAT_SERVICE_ACTION_START)) {
            mConnectionManager.connect(mPostingConnectionChangeListener);
        } else if (action.equals(CHAT_SERVICE_ACTION_STOP)) {
            mConnectionManager.disconnect();
        }

        return START_NOT_STICKY;
    }
}
