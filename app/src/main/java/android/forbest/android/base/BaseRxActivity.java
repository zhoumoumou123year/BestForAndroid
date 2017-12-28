package android.forbest.android.base;

import android.forbest.android.app.Global;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @Description: 管理RxJava生命周期，避免内存泄露，RxJava处理服务器返回
 * @Author : ZhouHui
 * @Date : 2017/12/26.
 */

public abstract class BaseRxActivity extends BaseActivity {

    /**
     * stop管理
     */
    private CompositeSubscription mSubscription2Stop;
    /**
     * destroy管理
     */
    private CompositeSubscription mSubscription2Destroy;

    protected abstract boolean needHandlerResult(BaseHttpBean result);

    /**
     * Rx处理服务器返回
     *
     * @param <T> 泛型Model
     * @return
     */
    public <T> ObservableTransformer<BaseHttpBean<T>, T> handleResult() {
        return upstream ->
                upstream.flatMap(result -> {
                    if (Global.CODE_SUCCESS.equals(result.getExecCode())) {
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

    public boolean addRxStop(Subscription subscription) {
        if (mSubscription2Stop == null) {
            throw new IllegalStateException("addUtilStop should be called between onStart and onStop");
        }
        mSubscription2Stop.add(subscription);
        return true;
    }

    public boolean addRxDestroy(Subscription subscription) {
        if (mSubscription2Destroy == null) {
            throw new IllegalStateException("addUtilDestroy should be called between onCreate and onDestroy");
        }
        mSubscription2Destroy.add(subscription);
        return true;
    }

    public void remove(Subscription subscription) {
        if (mSubscription2Stop == null && mSubscription2Destroy == null) {
            throw new IllegalStateException("remove should be not called after onDestroy");
        }
        if (mSubscription2Stop != null) {
            mSubscription2Stop.remove(subscription);
        }
        if (mSubscription2Destroy != null) {
            mSubscription2Destroy.remove(subscription);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSubscription2Stop == null) {
            throw new IllegalStateException("onStop called multiple times or onStart not celled");
        }
//        mSubscription2Stop.
    }
}
