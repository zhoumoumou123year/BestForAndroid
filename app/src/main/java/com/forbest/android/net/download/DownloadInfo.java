package com.forbest.android.net.download;


import com.forbest.android.api.ApiService;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/7/24.
 */
public class DownloadInfo {
    /* 存储位置 */
    private String savePath;
    /* 文件总长度 */
    private long contentLength;
    /* 下载长度 */
    private long readLength;
    /* 下载该文件的url */
    private String url;
    private ApiService service;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ApiService getService() {
        return service;
    }

    public void setService(ApiService service) {
        this.service = service;
    }
}

