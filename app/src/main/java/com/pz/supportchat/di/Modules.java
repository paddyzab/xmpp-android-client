package com.pz.supportchat.di;

import com.pz.supportchat.App;

public final class Modules {
    public static Object list(final App app) {
        return new AppModule(app);
    }
}
