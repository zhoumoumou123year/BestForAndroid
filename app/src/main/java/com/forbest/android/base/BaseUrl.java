package com.forbest.android.base;

import com.forbest.android.BuildConfig;

/**
 * Description:
 * Author: zhou
 * Date: 2017/12/16.
 */

public interface BaseUrl {
    /**
     * 开发环境
     */
    String DEV_URL = "http://10.0.1.87:8080/card-service-web/";
    /**
     * 测试环境
     */
    String CHECK_URL = "http://10.0.1.87:8080/card-service-web/";
    /**
     * 生产环境
     */
    String PRODUCT_URL = "http://10.0.1.87:8080/card-service-web/";

    String BASE_URL = BuildConfig.ENV_TYPE == 1 ? DEV_URL : (BuildConfig.ENV_TYPE == 2 ? CHECK_URL : PRODUCT_URL);


}
