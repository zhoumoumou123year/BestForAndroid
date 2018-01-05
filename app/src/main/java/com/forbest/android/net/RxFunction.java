package com.forbest.android.net;

import com.forbest.android.base.BaseHttpBean;

import io.reactivex.functions.Function;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/5.
 */

public class RxFunction<T> implements Function<BaseHttpBean<T, Void>, T> {

    @Override
    public T apply(BaseHttpBean<T, Void> httpBean) throws Exception {
        String resultCode = httpBean.getExecCode();
        if (!HttpCode.SUCCESS.equals(resultCode)) {

        }
        return httpBean.getExecData();
    }
}
