package com.pz.supportchat.storage;

public interface KeyValueStorage {

    void delete();

    void storeString(String key, String value);

    boolean containsLoginCredentials();
}
