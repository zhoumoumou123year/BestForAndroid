package com.forbest.android.business.login;

import com.forbest.android.api.Api;
import com.forbest.android.net.RxFunction;
import com.forbest.android.net.RxSchedulers;

import io.reactivex.Observable;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/5.
 */

public class LoginModel {

    public Observable<LoginBean> getLogin(String userName, String password){
        return Api.getApiService().login(userName, password).map(new RxFunction<LoginBean>()).compose(RxSchedulers.<LoginBean>io2Main());
    }
}
