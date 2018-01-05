package com.forbest.android.base;

import com.forbest.android.net.HttpCode;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @Description: 管理RxJava生命周期，避免内存泄露，RxJava处理服务器返回
 * @Author : ZhouHui
 * @Date : 2017/12/26.
 */

public abstract class BaseRxActivity extends BaseActivity {

    /**
     * stop管理
     */
    private CompositeDisposable mDisposable2Stop;
    /**
     * destroy管理
     */
    private CompositeDisposable mDisposable2Destroy;

    protected abstract boolean needHandlerResult(BaseHttpBean result);

    /**
     * Rx处理服务器返回
     *
     * @param <T> 泛型Model
     * @return
     */
    public <T, V> ObservableTransformer<BaseHttpBean<T, V>, T> handleResult() {
        return upstream ->
                upstream.flatMap(result -> {
                    if (HttpCode.SUCCESS.equals(result.getExecCode())) {
                        return createData(result.getExecData());
                    } else if (!needHandlerResult(result)) {
                        return Observable.error(new Exception(result.getExecMsg()));
                    }
                    return Observable.empty();
                });
    }

    private <T> Observable<T> createData(T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public boolean addRxStop(Disposable disposable) {
        if (mDisposable2Stop == null) {
            throw new IllegalStateException("addUtilStop should be called between onStart and onStop");
        }
        mDisposable2Stop.add(disposable);
        return true;
    }

    public boolean addRxDestroy(Disposable disposable) {
        if (mDisposable2Destroy == null) {
            throw new IllegalStateException("addUtilDestroy should be called between onCreate and onDestroy");
        }
        mDisposable2Destroy.add(disposable);
        return true;
    }

    public void remove(Disposable disposable) {
        if (mDisposable2Stop == null && mDisposable2Destroy == null) {
            throw new IllegalStateException("remove should be not called after onDestroy");
        }
        if (mDisposable2Stop != null) {
            mDisposable2Stop.remove(disposable);
        }
        if (mDisposable2Destroy != null) {
            mDisposable2Destroy.remove(disposable);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable2Stop == null) {
            throw new IllegalStateException("onStop called multiple times or onStart not celled");
        }
        mDisposable2Stop.dispose();
        mDisposable2Stop = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable2Destroy == null) {
            throw new IllegalStateException("on Destroy called multiple times or onCreate not called");
        }
        mDisposable2Destroy.dispose();
        mDisposable2Destroy = null;
    }
}
