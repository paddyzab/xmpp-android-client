package com.pz.supportchat.login_to_chat;

import android.text.TextUtils;
import com.pz.supportchat.PostingConnectionChangeListener;
import com.pz.supportchat.storage.SharedPreferencesKeyValueStorage;
import com.pz.supportchat.xmpp.ConnectionManager;
import com.pz.supportchat.xmpp.XMPPConnectionProvider;

public class LoginControllerImpl implements LoginController {

    private ConnectionManager mConnectionManager;
    private XMPPConnectionProvider mXMPPConnectionProvider;
    private PostingConnectionChangeListener mPostingConnectionChangeListener;
    private SharedPreferencesKeyValueStorage mSharedPreferencesKeyValueStorage;

    public LoginControllerImpl(ConnectionManager connectionManager, XMPPConnectionProvider xMPPConnectionProvider, PostingConnectionChangeListener postingConnectionChangeListener, SharedPreferencesKeyValueStorage sharedPreferencesKeyValueStorage) {
        mConnectionManager = connectionManager;
        mXMPPConnectionProvider = xMPPConnectionProvider;
        mPostingConnectionChangeListener = postingConnectionChangeListener;
        mSharedPreferencesKeyValueStorage = sharedPreferencesKeyValueStorage;
    }

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener loginFinishedListener) {
        loginAndStartChat(username, password, loginFinishedListener);

        mSharedPreferencesKeyValueStorage.storeString(mSharedPreferencesKeyValueStorage.LOGIN_KEY,
                username);
        mSharedPreferencesKeyValueStorage
                .storeString(mSharedPreferencesKeyValueStorage.PASSWORD_KEY, password);
    }

    @Override
    public void reconnectToServer() {
        if (!mXMPPConnectionProvider.getConnection().isConnected()) {
            mConnectionManager.connect(mPostingConnectionChangeListener);
        }
    }

    private void loginAndStartChat(final String username, final String password, final OnLoginFinishedListener loginFinishedListener) {

        boolean error = false;

        if (TextUtils.isEmpty(username)) {
            loginFinishedListener.onUsernameError();
            error = true;
        }

        if (TextUtils.isEmpty(password)) {
            loginFinishedListener.onPasswordError();
            error = true;
        }

        if (!error) {
            mConnectionManager.login(username,
                    password);
            if (mXMPPConnectionProvider.getConnection().isAuthenticated()) {
                loginFinishedListener.onSuccess();
            }
        }
    }
}
