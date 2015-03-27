package com.pz.supportchat;

import com.squareup.otto.Bus;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class MainThreadBus extends Bus {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    MainThreadBus.super.post(event);
                }
            });
        }
    }

    public void inject(Context context) {
        App.get(context).inject(this);
    }
}