package com.forbest.android.net;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.forbest.android.exception.ApiException;
import com.forbest.android.util.ToastUtil;

import java.io.EOFException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Description: 自定义观察者
 * @Author : ZhouHui
 * @Date : 2018/1/3.
 */

public abstract class RxObserver<T> implements Observer<T> {

    private RxManager mRxManager;
    private int mWhichRequest;
    private String mKey;
    private boolean mIsShowDialog;
    private Dialog mDialog;
    private Context mContext;

    public RxObserver(Context context, String key, int whichRequest, boolean isShowDialog) {
        this.mContext = context;
        this.mKey = key;
        this.mWhichRequest = whichRequest;
        this.mIsShowDialog = isShowDialog;
        mDialog = new ProgressDialog(context);
        mDialog.setTitle("请稍后");
        mRxManager = RxManager.getInstance();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mRxManager.add(mKey, d);
        if (mIsShowDialog) {
            mDialog.show();
        }
        onStart(mWhichRequest);
    }

    @Override
    public void onNext(T t) {
        onSuccess(mWhichRequest, t);
    }

    @Override
    public void onError(Throwable e) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (e instanceof EOFException || e instanceof ConnectException || e instanceof SocketException || e instanceof BindException || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            ToastUtil.show("网络异常，请稍后重试！");
        } else if (e instanceof ApiException) {
            onError(mWhichRequest, e);
        } else {
            ToastUtil.show("未知错误！");
        }
    }

    @Override
    public void onComplete() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public abstract void onSuccess(int whichRequest, T t);

    public abstract void onError(int whichRequest, Throwable throwable);

    public void onStart(int whichRequest) {

    }
}
