package com.pz.supportchat.di;

import com.pz.supportchat.App;
import com.pz.supportchat.MainThreadBus;
import com.pz.supportchat.NetworkChangeReceiver;
import com.pz.supportchat.PostingConnectionChangeListener;
import com.squareup.otto.Bus;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        DataModule.class,
        UiModule.class,
        XMPPModule.class
        },injects = {
        App.class,
        NetworkChangeReceiver.class,
        PostingConnectionChangeListener.class
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
    Bus provideBus() {
        return new MainThreadBus();
    }
}
