package com.pz.supportchat.di;

import com.pz.supportchat.xmpp.ChatManager;
import com.pz.supportchat.xmpp.ChatService;
import com.pz.supportchat.xmpp.RosterManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                XMPPConnectionProvider.class,
                ChatService.class,
                ChatManager.class,
                RosterManager.class
        },
        complete = false,
        library = true
)
final class XMPPModule {
    
    @Provides
    @Singleton
    XMPPConnectionProvider provideXMPPConnectionProvider() {
        return new XMPPConnectionProvider();
    }
    
    @Provides
    @Singleton
    ChatManager provideChatManager() {
        return new ChatManager();
    }

    @Provides
    @Singleton
    RosterManager provideRosterManager() {
        return new RosterManager();
    }
}
