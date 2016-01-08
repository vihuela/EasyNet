package com.hadlink.easynet.conf;

import android.content.Context;

import com.hadlink.easynet.util.NetConfig;

import java.util.HashMap;

public class NetConfigBuilder {

    private String response_cache = "lays_net_cache";
    private int response_cache_size = 5000;
    private int http_connect_timeout = 8000;
    private int http_read_timeout = 5000;
    private boolean print_body = true;
    private boolean log = true;
    private String log_tag = "lays";
    private Context appContext;
    private HashMap<String, String> header;

    public NetConfigBuilder responseCacheDir(String response_cache) {
        this.response_cache = response_cache;
        return this;
    }

    public NetConfigBuilder responseCacheSize(int response_cache_size) {
        this.response_cache_size = response_cache_size;
        return this;
    }

    public NetConfigBuilder connectionTimeout(int http_connect_timeout) {
        this.http_connect_timeout = http_connect_timeout;
        return this;
    }

    public NetConfigBuilder readTimeout(int http_read_timeout) {
        this.http_read_timeout = http_read_timeout;
        return this;
    }

    public NetConfigBuilder printResponseBody(boolean print_body) {
        this.print_body = print_body;
        return this;
    }

    public NetConfigBuilder log(boolean log) {
        this.log = log;
        return this;
    }

    public NetConfigBuilder logTag(String log_tag) {
        this.log_tag = log_tag;
        return this;
    }

    public NetConfigBuilder context(Context app) {
        this.appContext = app.getApplicationContext();
        return this;
    }

    public NetConfigBuilder header(HashMap<String, String> header) {
        this.header = header;
        return this;
    }

    public NetConfig createNetConfig() {
        return new NetConfig(response_cache, response_cache_size, http_connect_timeout, http_read_timeout, print_body, log, log_tag, appContext, header);
    }
}