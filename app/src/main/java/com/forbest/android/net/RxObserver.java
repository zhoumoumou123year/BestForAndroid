package com.forbest.android.net;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

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
    }

    @Override
    public void onNext(T t) {
        onSuccess(mWhichRequest, t);
    }

    @Override
    public void onError(Throwable e) {
        onError(mWhichRequest, e);
    }

    @Override
    public void onComplete() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public abstract void onSuccess(int whichRequest, T t);

    public abstract void onError(int whichRequest, Throwable throwable);
}
