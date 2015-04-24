package com.pz.supportchat.notifications;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.bus_events.NewMessageEvent;
import com.squareup.otto.Subscribe;

public class NotificationService extends Service {


    protected NotificationsProvider mNotificationsProvider;
    protected MainThreadBus mBus;
    private final IBinder mBinder = new NotificationServiceBinder();

    public interface INotificationService {
        public void startNotificationService(MainThreadBus bus, NotificationsProvider provider);
    }

    public class NotificationServiceBinder extends Binder implements  INotificationService {
        @Override
        public void startNotificationService(MainThreadBus bus, NotificationsProvider provider) {
            NotificationService.this.mBus = bus;
            NotificationService.this.mNotificationsProvider = provider;
            mBus.register(this);
        }
    }

    public NotificationService() {
        super();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Subscribe
    public void onNewMessageRecieved(final NewMessageEvent newMessageEvent) {
        mNotificationsProvider.getNewMessageNotification(this, newMessageEvent.mInternalMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }
}
