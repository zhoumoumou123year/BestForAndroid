package com.forbest.android.net;

import com.forbest.android.base.BaseHttpBean;
import com.forbest.android.business.login.LoginBean;
import com.forbest.android.exception.ApiException;
import com.forbest.android.util.LogUtil;

import io.reactivex.functions.Function;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/5.
 */

public class RxFunction<T> implements Function<BaseHttpBean<T>, T> {

    @Override
    public T apply(BaseHttpBean<T> httpBean) throws Exception {
        String resultCode = httpBean.getExecCode();
        if (!HttpCode.SUCCESS.equals(resultCode)) {
            throw new ApiException(resultCode, httpBean.getExecMsg());
        }
        return httpBean.getExecData();
    }

}
