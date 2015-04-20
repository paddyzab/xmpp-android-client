package com.pz.supportchat.login_to_chat;

public interface LoginPresenter {

    public void validateCredentials(final String username, final String password);

    void setLoginView(LoginView loginView);

    void reconnectToServer();
}
