package com.pz.supportchat.di;


import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.notifications.NotificationService;
import com.pz.supportchat.notifications.NotificationsProvider;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                NotificationService.class,
                NotificationsProvider.class
        },
        complete = false,
        library = true)
final class NotificationModule {

    
    @Provides
    NotificationService providesNotificationService(final MainThreadBus bus, final NotificationsProvider notificationsProvider) {
        return new NotificationService(bus, notificationsProvider);
        
    }
}
