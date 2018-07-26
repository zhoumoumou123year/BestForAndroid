package com.forbest.android.net.download;

/**
 * @Description: 文件下状态
 * @Author : ZhouHui
 * @Date : 2018/7/26.
 */
public class DownloadState {

    /**
     * 下载之前
     */
    public static final int DOWNLOAD_PRE = 0;
    /**
     * 下载中
     */
    public static final int DOWNLOAD_ING = 1;
    /**
     * 下载完成
     */
    public static final int DOWNLOAD_COMPETED = 2;
    /**
     * 下载暂停
     */
    public static final int DOWNLOAD_PAUSE = 3;

    /**
     * 下载失败
     */
    public static final int DOWNLOAD_FAILED = 4;

    /**
     * 下载状态
     */
    public static int STATE = DownloadState.DOWNLOAD_PRE;

}
