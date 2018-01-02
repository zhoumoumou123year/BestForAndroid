package android.forbest.android.api;

import android.forbest.android.base.BaseHttpBean;
import android.forbest.android.business.login.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Description: 所有的Api请求
 * Author: zhou
 * Date: 2017/12/16.
 */

public interface ApiService {

    // 登录
    @POST("user/login")
    Observable<BaseHttpBean<LoginBean, Void>> login(@Field("loginName") String loginName, @Field("password") String password);

}
