package com.example.pl.testhello.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * desc:
 * created by pl at 2018/12/20
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
