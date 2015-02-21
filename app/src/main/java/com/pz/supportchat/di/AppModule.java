package com.pz.supportchat.di;

import com.halfbit.tinybus.Bus;
import com.halfbit.tinybus.TinyBus;
import com.pz.supportchat.App;
import com.pz.supportchat.NetworkChangeReceiver;
import com.pz.supportchat.PostingConnectionChangeListener;

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
        return TinyBus.from(app);
    }
}
