package com.forbest.android.exception;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

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
            LogUtils.e("onError: SocketTimeoutException----" + SOCKET_CONNECT_TIME_OUT);
            ToastUtils.showShort(SOCKET_CONNECT_TIME_OUT);
            onError.accept(new Throwable(SOCKET_CONNECT_TIME_OUT));
        } else if (t instanceof ConnectException) {
            LogUtils.e("onError: ConnectException----" + CONNECT_EXCEPTION);
            ToastUtils.showShort(CONNECT_EXCEPTION);
            onError.accept(new Throwable(CONNECT_EXCEPTION));
        } else if (t instanceof UnknownHostException) {
            LogUtils.e("onError: UnknowHostException");
            ToastUtils.showShort(UNKNOW_EXCEPTION);
            onError.accept(new Throwable(UNKNOW_EXCEPTION));
        } else {
            LogUtils.e("onError: ----" + t.getMessage());
            onError.accept(t);
        }
    }
}
