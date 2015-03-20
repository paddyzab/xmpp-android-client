package com.pz.supportchat.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesKeyValueStorage implements KeyValueStorage {

    private final SharedPreferences mSharedPreferences;
    private final String EMPTY_STRING = "";

    public SharedPreferencesKeyValueStorage(final Context context, final String fileName) {
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public void delete() {
        final Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public final void storeString(final String key, final String value) {
        final  Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public final String getString(final String key) {
        return mSharedPreferences.getString(key, EMPTY_STRING);
    }
}
