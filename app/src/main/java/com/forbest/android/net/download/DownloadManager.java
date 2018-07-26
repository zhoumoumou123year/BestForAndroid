package com.forbest.android.net.download;

import android.os.Environment;

import com.blankj.utilcode.util.LogUtils;
import com.forbest.android.api.ApiService;
import com.forbest.android.base.BaseUrl;
import com.forbest.android.util.FileUtil;

import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description: Retrofit2 + RxJava2 文件下载，支持断点续传
 * @Author : ZhouHui
 * @Date : 2018/7/25.
 */
public class DownloadManager implements DownloadProgressListener {

    private DownloadInfo info;
    private ProgressListener progressObserver;
    private File outFile;
    private ApiService service;
    private Subscription mSub;

    private DownloadManager() {
        info = new DownloadInfo();
        outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "CardGame.apk");
        info.setSavePath(outFile.getAbsolutePath());
    }

    public static DownloadManager getInstance() {
        return Holder.manager;
    }

    @Override
    public void progress(long read, final long contentLength, final boolean done) {
        LogUtils.d("progress : 进度", "read = " + read, "contentLength = " + contentLength);
        // 该方法仍然是在子线程，如果想要调用进度回调，需要切换到主线程，否则的话，会在子线程更新UI，直接错误
        // 如果断电续传，重新请求的文件大小是从断点处到最后的大小，不是整个文件的大小，info中的存储的总长度是
        // 整个文件的大小，所以某一时刻总文件的大小可能会大于从某个断点处请求的文件的总大小。此时read的大小为
        // 之前读取的加上现在读取的
        if (info.getContentLength() > contentLength) {
            read = read + (info.getContentLength() - contentLength);
        } else {
            info.setContentLength(contentLength);
        }
        info.setReadLength(read);

        Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                if (progressObserver != null) {
                    progressObserver.progressChanged(info.getReadLength(), info.getContentLength(), done);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 开始下载
     *
     * @param url
     */
    public void start(String url) {
        info.setUrl(url);
        final DownloadInterceptor interceptor = new DownloadInterceptor(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseUrl.BASE_URL)
                .build();
        if (service == null) {
            service = retrofit.create(ApiService.class);
            info.setService(service);
        } else {
            service = info.getService();
        }
        downLoad();
    }

    /**
     * 开始下载
     */
    private void downLoad() {
        if (DownloadState.STATE == DownloadState.DOWNLOAD_ING) {
            LogUtils.d("正在下载");
            return;
        }
        LogUtils.d("下载信息", info.toString());
        service.download("bytes=" + info.getReadLength() + "-", info.getUrl())
                // 指定线程
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RetryWhenNetworkException())
                .doOnNext(responseBody -> {
                    DownloadState.STATE = DownloadState.DOWNLOAD_ING;
                })
                .map(responseBody -> {
                    try {
                        // 写入文件
                        FileUtil.writeCache(responseBody, new File(info.getSavePath()), info.getReadLength(), info.getContentLength());
                    } catch (IOException e) {
                        LogUtils.e("异常", e.toString());
                    }
                    return info;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new FlowableSubscriber<DownloadInfo>() {
                            @Override
                            public void onSubscribe(Subscription s) {
                                mSub = s;
                                mSub.request(1);
                            }

                            @Override
                            public void onNext(DownloadInfo downloadInfo) {
                                mSub.request(1);
                                LogUtils.d("下载", "onNext");
                            }

                            @Override
                            public void onError(Throwable t) {
                                DownloadState.STATE = DownloadState.DOWNLOAD_FAILED;
                                info.setReadLength(0);
                                LogUtils.e("下载失败", t);
                            }

                            @Override
                            public void onComplete() {
                                DownloadState.STATE = DownloadState.DOWNLOAD_COMPETED;
                                info.setReadLength(0);
                                LogUtils.d("下载完成");
                            }
                        });
    }

    /**
     * 暂停下载
     */
    public void pause() {
        if (mSub != null) {
            DownloadState.STATE = DownloadState.DOWNLOAD_PAUSE;
            mSub.cancel();
        }
    }

    /**
     * 继续下载
     */
    public void reStart() {
        downLoad();
    }

    public void setProgressListener(ProgressListener progressObserver) {
        this.progressObserver = progressObserver;
    }

    /**
     * 进度监听
     */
    public interface ProgressListener {
        void progressChanged(long read, long contentLength, boolean done);
    }

    public static class Holder {
        private static DownloadManager manager = new DownloadManager();
    }

}
