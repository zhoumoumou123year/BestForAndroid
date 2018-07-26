package com.forbest.android.net.download;

/**
 * @Description: 下载进度回调
 * @Author : ZhouHui
 * @Date : 2018/7/24.
 */
public interface DownloadProgressListener {

    /**
     * 下载进度
     */
    void progress(long read, long count, boolean done);
}
