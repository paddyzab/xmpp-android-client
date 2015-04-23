package com.pz.supportchat.notifications;

import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.bus_events.NewMessageEvent;
import com.squareup.otto.Subscribe;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {


    protected NotificationsProvider mNotificationsProvider;
    protected MainThreadBus mBus;

    
    public NotificationService() {
        super();
        
    }
    
    public NotificationService(MainThreadBus bus, NotificationsProvider notificationsProvider) {
        this();

        mBus = bus;
        mNotificationsProvider = notificationsProvider;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe
    public void onNewMessageRecieved(final NewMessageEvent newMessageEvent) {
        mNotificationsProvider.getNewMessageNotification(this, newMessageEvent.mInternalMessage);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }
}
