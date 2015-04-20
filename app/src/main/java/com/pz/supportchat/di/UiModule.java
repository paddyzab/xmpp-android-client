package com.pz.supportchat.di;

import com.pz.supportchat.Intents;
import com.pz.supportchat.PostingConnectionChangeListener;
import com.pz.supportchat.contacts_list.ContactsActivity;
import com.pz.supportchat.current_chat.ChatActivity;
import com.pz.supportchat.login_to_chat.LoginController;
import com.pz.supportchat.login_to_chat.LoginControllerImpl;
import com.pz.supportchat.login_to_chat.LoginPresenter;
import com.pz.supportchat.login_to_chat.LoginPresenterImpl;
import com.pz.supportchat.login_to_chat.LoginToChatActivity;
import com.pz.supportchat.storage.SharedPreferencesKeyValueStorage;
import com.pz.supportchat.xmpp.ConnectionManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;
import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                ChatActivity.class,
                LoginToChatActivity.class,
                ContactsActivity.class,
                LoginController.class,
                LoginPresenter.class
        },
        complete = false,
        library = true
)
final class UiModule {

    @Provides
    Intents provideIntentsFactory() {
        return new Intents();
    }

    @Provides
    LoginController providesLoginController(final ConnectionManager connectionManager,
                                            final XMPPConnectionProvider xMPPConnectionProvider,
                                            final PostingConnectionChangeListener postingConnectionChangeListener,
                                            final SharedPreferencesKeyValueStorage sharedPreferencesKeyValueStorage) {
        return new LoginControllerImpl(connectionManager,
                xMPPConnectionProvider, postingConnectionChangeListener, sharedPreferencesKeyValueStorage);
    }

    @Provides
    LoginPresenter providesLoginPresenter() {
        return new LoginPresenterImpl();
    }
}
