package com.forbest.android.util;

import android.content.Context;

import com.forbest.android.BuildConfig;
import com.forbest.android.app.MyApplication;
import android.widget.Toast;

/**
 * @Description: Toast工具类
 * @Author : ZhouHui
 * @Date : 2017/12/26.
 */

public class ToastUtil {

    private static Context mContext = MyApplication.getApplication();
    private static Toast mToast;

    public static void show(int resId) {
        show(mContext.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(mContext.getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void showDebug(CharSequence text) {
        if (BuildConfig.DEBUG) {
            show(text, Toast.LENGTH_SHORT);
        }
    }

    public static void show(CharSequence text, int duration) {
        if (TextUtil.isEmpty(text)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

}
