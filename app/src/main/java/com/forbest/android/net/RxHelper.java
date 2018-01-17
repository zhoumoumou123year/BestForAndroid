package com.forbest.android.net;

import com.forbest.android.base.BaseHttpBean;
import com.forbest.android.exception.ApiException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 对解析结果进行预处理
 * @Author : ZhouHui
 * @Date : 2017/12/20.
 */

public class RxHelper {

    public static <T> Observable<T> craeteData(final  T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {

            }
        });
    }

    /**
     * 获取execData数据
     * @param <T> execData
     * @param <L> execDatas 可为Void
     */
    public class ExecDataFunc<T, L> implements Function<BaseHttpBean<T>, T> {
        @Override
        public T apply(BaseHttpBean<T> baseHttpBean) {
            if (!HttpCode.SUCCESS.equals(baseHttpBean.getExecCode())) {
                throw new ApiException(Integer.valueOf(baseHttpBean.getExecCode()), baseHttpBean.getExecMsg());
            }
            return baseHttpBean.getExecData();
        }
    }

    /**
     * io发起，回调在主线程
     * @param observable 被观察者
     * @param observer 观察者
     * @param <T> vo
     */
    public <T> void toSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
