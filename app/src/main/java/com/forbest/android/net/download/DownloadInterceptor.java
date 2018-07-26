package com.forbest.android.net.download;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @Description: 自定义拦截器监听下载进度
 * @Author : ZhouHui
 * @Date : 2018/7/24.
 */
public class DownloadInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public DownloadInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), listener))
                .build();
    }


}
