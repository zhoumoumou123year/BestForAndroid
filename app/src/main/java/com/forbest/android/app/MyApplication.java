package com.forbest.android.app;

import android.app.Application;
import android.content.Context;
import com.forbest.android.net.HttpManager;

/**
 * Description: 自定义Application
 * Author: zhou
 * Date: 2017/12/17.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    public static Context getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        HttpManager.getInstance().init(this);
    }
}