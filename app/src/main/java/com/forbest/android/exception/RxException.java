package com.forbest.android.exception;


import com.forbest.android.util.LogUtil;
import com.forbest.android.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;

/**
 * @Description: 自定义Rx异常
 * @Author : ZhouHui
 * @Date : 2018/1/2.
 */

public class RxException<T extends Throwable> implements Consumer<T> {

    private static final String TAG = "RxException";

    private static final String SOCKET_CONNECT_TIME_OUT = "socket网络连接超时，请检查您的网络状态！";

    private static final String CONNECT_EXCEPTION = "网络连接异常，请检查您的网络状态！";

    private static final String UNKNOW_EXCEPTION = "网络异常，请检查您的网络状态！";

    private Consumer<? super Throwable> onError;

    public RxException(Consumer<? super Throwable> onError) {
        this.onError = onError;
    }

    @Override
    public void accept(T t) throws Exception{
        if (t instanceof SocketTimeoutException) {
            LogUtil.e(TAG, "onError: SocketTimeoutException----" + SOCKET_CONNECT_TIME_OUT);
            ToastUtil.show(SOCKET_CONNECT_TIME_OUT);
            onError.accept(new Throwable(SOCKET_CONNECT_TIME_OUT));
        } else if (t instanceof ConnectException) {
            LogUtil.e(TAG, "onError: ConnectException----" + CONNECT_EXCEPTION);
            ToastUtil.show(CONNECT_EXCEPTION);
            onError.accept(new Throwable(CONNECT_EXCEPTION));
        } else if (t instanceof UnknownHostException) {
            LogUtil.e(TAG, "onError: UnknowHostException");
            ToastUtil.show(UNKNOW_EXCEPTION);
            onError.accept(new Throwable(UNKNOW_EXCEPTION));
        } else {
            LogUtil.e(TAG, "onError: ----" + t.getMessage());
            onError.accept(t);
        }
    }
}
