package com.hadlink.easynet.conf;

import android.content.Context;

import com.hadlink.easynet.util.NetConfig;

import java.io.File;
import java.util.HashMap;

public class NetConfigBuilder {

    private File response_cache;
    private int response_cache_size = 5 * 1024 * 1024;
    private int http_connect_timeout = 8 * 1000;
    private int http_read_timeout = 5 * 1000;
    private boolean print_body = true;
    private boolean log = true;
    private String log_tag = "easyNet";
    private Context appContext;
    private HashMap<String, String> header;
    private CacheType cacheType = CacheType.ONLY_NETWORK;


    /**
     * cacheType
     */
    public NetConfigBuilder cacheType(CacheType cacheType) {
        this.cacheType = cacheType;
        return this;
    }

    /**
     * local cache dir
     */
    public NetConfigBuilder responseCacheDir(File response_cache) {
        this.response_cache = response_cache;
        return this;
    }

    /**
     * local cache size
     */
    public NetConfigBuilder responseCacheSize(int response_cache_size) {
        this.response_cache_size = response_cache_size;
        return this;
    }

    /**
     * readTime
     *
     * @param http_connect_timeout millisecond
     */
    public NetConfigBuilder connectionTimeout(int http_connect_timeout) {
        this.http_connect_timeout = http_connect_timeout;
        return this;
    }

    /**
     * timeout
     *
     * @param http_read_timeout millisecond
     */
    public NetConfigBuilder readTimeout(int http_read_timeout) {
        this.http_read_timeout = http_read_timeout;
        return this;
    }

    /**
     * if response is json ,print json body
     */
    public NetConfigBuilder printResponseBody(boolean print_body) {
        this.print_body = print_body;
        return this;
    }

    /**
     * request log
     */
    public NetConfigBuilder log(boolean log) {
        this.log = log;
        return this;
    }

    /**
     * log filter tag
     */
    public NetConfigBuilder logTag(String log_tag) {
        this.log_tag = log_tag;
        return this;
    }

    /**
     * must set Context
     */
    public NetConfigBuilder context(Context app) {
        this.appContext = app.getApplicationContext();
        return this;
    }

    /**
     * request header
     */
    public NetConfigBuilder header(HashMap<String, String> header) {
        this.header = header;
        return this;
    }

    public NetConfig createNetConfig() {
        return new NetConfig(response_cache, response_cache_size, http_connect_timeout, http_read_timeout, print_body, log, log_tag, appContext, header, cacheType);
    }
}