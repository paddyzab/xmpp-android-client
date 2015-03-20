package com.pz.supportchat.di;

import com.pz.supportchat.Intents;
import com.pz.supportchat.current_chat.ChatActivity;
import com.pz.supportchat.login_to_chat.LoginToChatActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(
        injects = {
                ChatActivity.class,
                LoginToChatActivity.class
        },
        complete = false,
        library = true
)
final class UiModule {

    @Provides
    @Singleton
    Intents provideIntentsFactory() {
        return new Intents();
    }
}
