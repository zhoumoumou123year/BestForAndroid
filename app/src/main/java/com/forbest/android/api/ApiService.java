package com.forbest.android.api;

import com.forbest.android.base.BaseHttpBean;
import com.forbest.android.business.login.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Description: 所有的Api请求
 * Author: zhou
 * Date: 2017/12/16.
 */

public interface ApiService {

    // 登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseHttpBean<LoginBean>> login(@Field("loginName") String loginName, @Field("password") String password);

}
