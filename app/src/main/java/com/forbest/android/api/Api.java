package com.forbest.android.api;

import com.forbest.android.net.HttpManager;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/2.
 */

public class Api {

    private static volatile ApiService mApiService = null;

    /**
     * 双重锁定单例
     */
    public static ApiService getApiService() {
        if (mApiService == null) {
            synchronized (ApiService.class) {
                if (mApiService == null) {
                    mApiService = HttpManager.getInstance().getRetrofit().create(ApiService.class);
                }
            }
        }
        return mApiService;
    }
}
