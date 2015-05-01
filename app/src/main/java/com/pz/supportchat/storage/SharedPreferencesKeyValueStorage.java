package com.pz.supportchat.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class SharedPreferencesKeyValueStorage implements KeyValueStorage {

    private final SharedPreferences mSharedPreferences;
    private final String EMPTY_STRING = "";

    public final String PASSWORD_KEY = "_password";
    public final String LOGIN_KEY = "_login";

    public SharedPreferencesKeyValueStorage(final Context context, final String fileName) {
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public void delete() {
        final Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public final void storeString(final String key, final String value) {
        final  Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public boolean containsLoginCredentials() {
        return contains(LOGIN_KEY) && contains(PASSWORD_KEY);
    }

    private boolean contains(final String key) {
        return mSharedPreferences.contains(key) && !TextUtils.isEmpty(mSharedPreferences.getString(key, EMPTY_STRING));
    }

    public final String getString(final String key) {
        return mSharedPreferences.getString(key, EMPTY_STRING);
    }
}
