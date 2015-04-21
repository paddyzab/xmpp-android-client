package com.pz.supportchat.login_to_chat;

import com.pz.supportchat.PostingConnectionChangeListener;

public interface LoginView {

    void connectionChanged(PostingConnectionChangeListener.XMPPConnectionStatus status);

    public void showProgress();

    public void hideProgress();

    public void setUsernameError();

    public void setPasswordError();

    public void navigateToContactsList();
}
