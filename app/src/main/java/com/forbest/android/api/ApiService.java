package com.forbest.android.api;

import com.forbest.android.base.BaseHttpBean;
import com.forbest.android.vo.login.LoginBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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

    // 上传
    @Multipart
    @POST("upload")
    Observable<ResponseBody> upload(@Part("description") ResponseBody description, @Part MultipartBody.Part file);

    // 下载
    @Streaming
    @GET
    Flowable<ResponseBody> download(@Header("RANGE") String start, @Url String url);

}
