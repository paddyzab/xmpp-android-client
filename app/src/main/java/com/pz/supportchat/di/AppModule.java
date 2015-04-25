package com.pz.supportchat.di;

import android.app.Application;
import com.pz.supportchat.App;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.NetworkChangeReceiver;
import com.pz.supportchat.PostingConnectionChangeListener;
import com.pz.supportchat.notifications.NotificationService;
import com.pz.supportchat.notifications.NotificationsProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(includes = {
        DataModule.class,
        UiModule.class,
        XMPPModule.class
        },injects = {
        App.class,
        NetworkChangeReceiver.class,
        PostingConnectionChangeListener.class,
        MainThreadBus.class,
        NotificationService.class,
        NotificationsProvider.class
})
final class AppModule {
    private final App app;

    public AppModule(final App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides 
    @Singleton
    MainThreadBus provideBus() {
        return new MainThreadBus();
    }
}
