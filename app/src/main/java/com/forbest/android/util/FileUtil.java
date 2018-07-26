package com.forbest.android.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.ResponseBody;

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

    /**
     * 写入文件
     */
    public static void writeCache(ResponseBody responseBody, File file, long readLength, long contentLength) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (contentLength == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = contentLength;
        }

        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, readLength, allLength - readLength);
        byte[] buffer = new byte[1024 * 8];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }
}
