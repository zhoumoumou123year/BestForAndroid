package com.forbest.android.net.download;

/**
 * @Description: 重试当网络异常时
 * @Author : ZhouHui
 * @Date : 2018/7/25.
 */

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * retry条件
 */
public class RetryWhenNetworkException implements Function<Flowable<? extends Throwable>, Flowable<?>> {
    //    retry次数
    private int count = 3;
    //    延迟
    private long delay = 3000;
    //    叠加延迟
    private long increaseDelay = 3000;

    public RetryWhenNetworkException() {

    }

    public RetryWhenNetworkException(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    public RetryWhenNetworkException(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

    @Override
    public Flowable<?> apply(Flowable<? extends Throwable> observable) throws Exception {
        //压缩规则 合并后的结果是一个Observable<Wrapper>
        return observable
                .zipWith(Flowable.range(1, count + 1), (BiFunction<Throwable, Integer, Wrapper>)
                        Wrapper::new).flatMap((Function<Wrapper, Flowable<?>>) wrapper -> {
                            //转换规则
                            if ((wrapper.throwable instanceof ConnectException
                                    || wrapper.throwable instanceof SocketTimeoutException
                                    || wrapper.throwable instanceof TimeoutException)
                                    && wrapper.index < count + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                                return Flowable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);

                            }
                            return Flowable.error(wrapper.throwable);
                        });
    }


}