package com.pz.supportchat;

import android.app.Application;
import android.content.Context;
import com.pz.supportchat.di.Modules;
import dagger.ObjectGraph;

public class App extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        buildGraph();
    }

    public void inject(final Object injectable) {
        objectGraph.inject(injectable);
    }

    public static App get(final Context context) {
        return (App) context.getApplicationContext();
    }

    private void buildGraph() {
        objectGraph = ObjectGraph.create(Modules.list(this));
        inject(this);
    }
}
