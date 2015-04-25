package com.pz.supportchat.notifications;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.pz.supportchat.App;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.bus_events.NewMessageEvent;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class NotificationService extends Service {

    @Inject
    protected NotificationsProvider mNotificationsProvider;

    @Inject
    protected MainThreadBus mBus;

    private NotificationManager notificationManager;
    private final static int NEW_MESSAGE_ID = 1;

    public NotificationService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).inject(this);

        mBus.register(this);
        notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Binding is not yet supported.");
    }

    @Subscribe
    public void onNewMessageRecieved(final NewMessageEvent newMessageEvent) {
        notificationManager.notify(NEW_MESSAGE_ID, mNotificationsProvider.getNewMessageNotification(this, newMessageEvent.mInternalMessage));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }
}
