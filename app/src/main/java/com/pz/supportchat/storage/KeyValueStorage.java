package com.pz.supportchat.storage;

public interface KeyValueStorage {

    String EMPTY_STRING = "";
    String PASSWORD_KEY = "_password";
    String LOGIN_KEY = "_login";

    void delete();

    void storeString(String key, String value);

    boolean containsLoginCredentials();
}
