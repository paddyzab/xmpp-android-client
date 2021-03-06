package com.pz.supportchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import javax.inject.Inject;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public enum InternetConnectionStatus {
        CONNECTED,
        DISCONNECTED
    }

    @Inject
    public MainThreadBus mBus;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        mBus.post(wifi.isAvailable() || mobile.isAvailable()
                ? InternetConnectionStatus.CONNECTED
                : InternetConnectionStatus.DISCONNECTED);
    }

    public void inject(Context context) {
        App.get(context).inject(this);
    }
}



