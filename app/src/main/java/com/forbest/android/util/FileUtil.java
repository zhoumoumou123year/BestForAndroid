package com.forbest.android.util;

import android.content.Context;

/**
 * Description:
 * Author: zhou
 * Date: 2017/12/17.
 */

public class FileUtil {

    /**
     * 如果存在SD卡则将缓存写入SD卡,否则写入手机内部存储
     */
    public static String getCacheDir(Context context) {
        String cacheDir;
        if (context.getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = context.getExternalCacheDir().toString();
        } else {
            cacheDir = context.getCacheDir().toString();
        }
        return cacheDir;
    }

    public static boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
}
