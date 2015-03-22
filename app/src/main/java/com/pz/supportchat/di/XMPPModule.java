package com.pz.supportchat.di;

import com.pz.supportchat.xmpp.ConnectionManager;
import com.pz.supportchat.xmpp.ChatService;
import com.pz.supportchat.xmpp.PostingMessageListener;
import com.pz.supportchat.xmpp.RosterManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                XMPPConnectionProvider.class,
                ChatService.class,
                ConnectionManager.class,
                RosterManager.class,
                PostingMessageListener.class
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
    ConnectionManager provideChatManager(final XMPPConnectionProvider xmppConnectionProvider) {
        return new ConnectionManager(xmppConnectionProvider.getConnection());
    }

    @Provides
    @Singleton
    RosterManager provideRosterManager() {
        return new RosterManager();
    }

    @Provides
    @Singleton
    PostingMessageListener providePostingMessageListener() {
        return new PostingMessageListener();
    }
}
