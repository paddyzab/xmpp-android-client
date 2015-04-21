package com.pz.supportchat.login_to_chat;

import com.pz.supportchat.MainThreadBus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import static com.pz.supportchat.PostingConnectionChangeListener.XMPPConnectionStatus;

public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener, LifecycleAware {

    private LoginView mLoginView;

    @Inject
    protected LoginController mLoginController;
    
    @Inject
    protected MainThreadBus mBus;

    @Override
    public void validateCredentials(final String username, final String password) {
        mLoginView.showProgress();
        mLoginController.login(username, password, this);
    }

    @Subscribe
    public void onConnectionStatusChanged(final XMPPConnectionStatus connectionStatus) {
        mLoginView.connectionChanged(connectionStatus);
    }

    @Override
    public void setLoginView(final LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void reconnectToServer() {
        mLoginController.reconnectToServer();
    }

    @Override
    public void onUsernameError() {
        mLoginView.setUsernameError();
        mLoginView.hideProgress();
    }

    @Override
    public void onPasswordError() {
        mLoginView.setPasswordError();
        mLoginView.hideProgress();
    }

    @Override
    public void onSuccess() {
        mLoginView.navigateToContactsList();
    }

    @Override
    public void onResume() {
        mBus.register(LoginPresenterImpl.this);
    }

    @Override
    public void onPause() {
        mBus.unregister(LoginPresenterImpl.this);
    }

    @Override
    public void onDestroy() {
        // nop
    }
}
