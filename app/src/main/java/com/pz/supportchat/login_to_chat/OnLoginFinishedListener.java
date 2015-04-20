package com.pz.supportchat.login_to_chat;

public interface OnLoginFinishedListener {

    public void onUsernameError();

    public void onPasswordError();

    public void onSuccess();

}
