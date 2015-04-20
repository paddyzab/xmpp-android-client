package com.pz.supportchat.login_to_chat;

import javax.inject.Inject;

public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener {

    private LoginView mLoginView;

    @Inject
    protected LoginController mLoginController;

    @Override
    public void validateCredentials(final String username, final String password) {
        mLoginView.showProgress();
        mLoginController.login(username, password, this);
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
}
