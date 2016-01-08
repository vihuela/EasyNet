package com.hadlink.easynet.util;

import android.content.Context;

import com.hadlink.easynet.conf.CacheType;

import java.io.File;
import java.util.HashMap;


/**
 * Net Config
 */
public class NetConfig {
    final HashMap<String, String> header;
    String LOG_TAG;
    File RESPONSE_CACHE;
    int RESPONSE_CACHE_SIZE;
    int HTTP_CONNECT_TIMEOUT;
    int HTTP_READ_TIMEOUT;
    boolean PRINT_BODY = true;
    boolean LOG = true;
    Context app = null;
    CacheType cacheType;


    public NetConfig(File RESPONSE_CACHE, int RESPONSE_CACHE_SIZE, int HTTP_CONNECT_TIMEOUT, int HTTP_READ_TIMEOUT, boolean PRINT_BODY, boolean LOG, String LOG_TAG, Context app, HashMap<String, String> header,CacheType cacheType) {
        this.RESPONSE_CACHE = RESPONSE_CACHE;
        this.RESPONSE_CACHE_SIZE = RESPONSE_CACHE_SIZE;
        this.HTTP_CONNECT_TIMEOUT = HTTP_CONNECT_TIMEOUT;
        this.HTTP_READ_TIMEOUT = HTTP_READ_TIMEOUT;
        this.PRINT_BODY = PRINT_BODY;
        this.LOG = LOG;
        this.LOG_TAG = LOG_TAG;
        this.app = app;
        this.header = header;
        this.cacheType = cacheType;
        JsonPrinter.TAG = this.LOG_TAG;
    }

    public String getLogTag() {
        return this.LOG_TAG;
    }

    public boolean isLog() {
        return this.LOG;
    }
}
