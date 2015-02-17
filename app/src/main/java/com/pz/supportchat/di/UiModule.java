package com.pz.supportchat.di;

import com.pz.supportchat.Intents;
import com.pz.supportchat.chat.CurrentChatActivity;
import com.pz.supportchat.chats_list.ChatsListActivity;
import com.pz.supportchat.login_to_chat.LoginToChatActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(
        injects = {
                CurrentChatActivity.class,
                ChatsListActivity.class,
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
