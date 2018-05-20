package com.mvp.app.common;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

public class ProjectApplication extends Application {

    private static ProjectApplication mAppController;

    public static ProjectApplication getInstance() {
        return mAppController;
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
