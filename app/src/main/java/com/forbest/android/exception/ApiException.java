package com.forbest.android.exception;

/**
 * @Description: 自定义异常处理
 * @Author : ZhouHui
 * @Date : 2017/12/20.
 */

public class ApiException extends RuntimeException {

    public String code;

    public String message;

    public ApiException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
