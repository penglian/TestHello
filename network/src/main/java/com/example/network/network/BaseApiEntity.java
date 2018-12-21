package com.example.network.network;


import java.io.File;

import okhttp3.Cache;

/**
 * 基础请求信息配置
 * Created by pl on 2018/4/26 0026.
 */

public abstract class BaseApiEntity {
    private String baseUrl;  //基地址

    private int maxAge = 60 * 60;  //网络时缓存
    private int maxStale = 60 * 60 * 24 * 28;  //无网络时缓存

    private int timeOutRead = 20;     //读取超时
    private int timeOutConnection = 10;  //连接超时

    private boolean isBombServer = true; //是否是Bomb

    private int diskCacheMaxSize = 50 * 1024 * 1024;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 构建磁盘缓存
     *
     * @return
     */
    public Cache getCache() {
        return new Cache(new File(CibNetworkApp.getContext().getCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"),
                diskCacheMaxSize);
    }

    public int getTimeOutRead() {
        return timeOutRead;
    }

    public void setTimeOutRead(int timeOutRead) {
        this.timeOutRead = timeOutRead;
    }

    public int getTimeOutConnection() {
        return timeOutConnection;
    }

    public void setTimeOutConnection(int timeOutConnection) {
        this.timeOutConnection = timeOutConnection;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isBombServer() {
        return isBombServer;
    }

    public void setBombServer(boolean bombServer) {
        isBombServer = bombServer;
    }

    public int getMaxStale() {
        return maxStale;
    }

    public void setMaxStale(int maxStale) {
        this.maxStale = maxStale;
    }
}
