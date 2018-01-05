package com.forbest.android.net;

import android.content.Context;

import com.forbest.android.BuildConfig;
import com.forbest.android.app.Global;
import com.forbest.android.base.BaseUrl;
import com.forbest.android.util.FileUtil;
import com.forbest.android.util.LogUtil;
import com.forbest.android.util.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description: 配置HttpClient及Retrofit
 * Author: zhou
 * Date: 2017/12/16.
 */

public class HttpManager {

    /**
     * Http缓存文件名
     */
    private static final String HTTP_CACHE_NAME = "HttpCache";
    private static final int HTTP_CACHE_MAX_SIZE = 1024 * 1024;
    private static final String TAG = "OkHttp";
    /**
     * 设置默认连接超时时间
     */
    private static final int DEFAULT_CONNTCT_TIME_OUT = 15;
    /**
     * 设置默认读取超时时间
     */
    private static final int DEFAULT_READ_TIME_OUT = 20;
    /**
     * 设置默认写入超时时间
     */
    private static final int DEFAULT_WRITE_TIME_OUT = 20;
    private static volatile HttpManager mInstance = null;
    private static Retrofit mRetrofit = null;
    private static OkHttpClient mOkHttpClient = null;
    private Context mContext;
    /**
     * 是否设置缓存
     */
    private boolean mIsUseCache;
    private int mMaxCacheTime = 60;

    /**
     * 双重锁单例模式
     */
    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    public int getMaxCacheTime() {
        return mMaxCacheTime;
    }

    public void setMaxCacheTime(int maxCacheTime) {
        mMaxCacheTime = maxCacheTime;
    }

    public boolean isUseCache() {
        return mIsUseCache;
    }

    public void setUseCache(boolean useCache) {
        mIsUseCache = useCache;
    }

    /**
     * 初始化HttpManager
     */
    public void init(Context context) {
        this.mContext = context;
        initOkHttpClient();
        initRetrofit();
    }

    /**
     * 初始化OkHttp
     */
    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 打印请求log日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        File cacheFile = new File(FileUtil.getCacheDir(mContext), HTTP_CACHE_NAME);
        Cache cache = new Cache(cacheFile, HTTP_CACHE_MAX_SIZE);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // 如果网络不可用或使用缓存
                if (!NetworkUtil.isNetworkConnected(mContext) || mIsUseCache) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    LogUtil.d(TAG, "网络不可用请求拦截");
                } else if (NetworkUtil.isNetworkConnected(mContext) && !mIsUseCache) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .build();
                    LogUtil.d(TAG, "网络可用请求拦截");
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkConnected(mContext)) {
                    LogUtil.d(TAG, "网络可用响应拦截");
                    response = response.newBuilder()
                            .header("Cache-Control", "public,max-age=" + mMaxCacheTime)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    LogUtil.d(TAG, "网络不可用响应拦截");
                }
                return response;
            }
        };
        builder.cache(cache);
        // 添加本地缓存拦截器，用来拦截本地缓存
        builder.interceptors().add(cacheInterceptor);
        // 添加网络拦截器，用来拦截网络数据
        builder.networkInterceptors().add(cacheInterceptor);
        // 设置头部
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header(Global.ACCESS_TOKEN, "")
                        .header(Global.DEVICE_NAME, "")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        builder.addInterceptor(headerInterceptor);
        // 设置超时
        builder.connectTimeout(DEFAULT_CONNTCT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS);
        // 错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}