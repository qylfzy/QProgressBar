package com.qiyou.qprogressbar;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by QiYou
 * on 2019/7/4
 */
public class MyApp extends Application {

    private static MyApp instance;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        refWatcher = initRefWatcher();
    }

    public static MyApp getInstance() {
        return instance;
    }

    private RefWatcher initRefWatcher() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
