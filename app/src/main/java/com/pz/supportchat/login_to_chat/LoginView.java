package com.pz.supportchat.login_to_chat;

public interface LoginView {

    public void showProgress();

    public void hideProgress();

    public void setUsernameError();

    public void setPasswordError();

    public void navigateToContactsList();
}
