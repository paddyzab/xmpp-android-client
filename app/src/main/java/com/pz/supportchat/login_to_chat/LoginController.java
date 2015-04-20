package com.pz.supportchat.login_to_chat;

public interface LoginController {

    public void login(final String username, final String password, OnLoginFinishedListener finishedListener);

    public void reconnectToServer();
}
