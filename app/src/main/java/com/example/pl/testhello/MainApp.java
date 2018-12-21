package com.example.pl.testhello;

import android.app.Application;
import com.example.network.network.CibNetworkApp;
import com.example.pl.testhello.base.BaseApplication;
import com.example.pl.testhello.base.CrashHandler;


/**
 * desc:
 * created by pl at 2018/12/20
 */
public class MainApp extends BaseApplication {
    private static Application INSTANCE;

    public static Application getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        init();
    }

    private void init() {
        CibNetworkApp.init(this);

        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());
    }

}
