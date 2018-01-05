package com.forbest.android.base;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/5.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void onError(int whichRequest, Throwable throwable);

    void showLoading(int whichRequest);

    void dismissLoading(int whichReqeust);

}
