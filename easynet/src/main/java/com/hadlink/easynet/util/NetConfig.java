package com.hadlink.easynet.util;

import android.content.Context;
import android.support.v4.util.ArrayMap;


/**
 * Net Config
 */
public class NetConfig {
    final ArrayMap<String, String> header;
    String LOG_TAG = "lays";
    String RESPONSE_CACHE = "lays_net_cache";
    int RESPONSE_CACHE_SIZE = 5000;
    int HTTP_CONNECT_TIMEOUT = 8000;
    int HTTP_READ_TIMEOUT = 5000;
    boolean PRINT_BODY = true;
    boolean LOG = true;
    Context app = null;

    public NetConfig(String RESPONSE_CACHE, int RESPONSE_CACHE_SIZE, int HTTP_CONNECT_TIMEOUT, int HTTP_READ_TIMEOUT, boolean PRINT_BODY, boolean LOG, String LOG_TAG, Context app, ArrayMap<String, String> header) {
        this.RESPONSE_CACHE = RESPONSE_CACHE;
        this.RESPONSE_CACHE_SIZE = RESPONSE_CACHE_SIZE;
        this.HTTP_CONNECT_TIMEOUT = HTTP_CONNECT_TIMEOUT;
        this.HTTP_READ_TIMEOUT = HTTP_READ_TIMEOUT;
        this.PRINT_BODY = PRINT_BODY;
        this.LOG = LOG;
        this.LOG_TAG = LOG_TAG;
        this.app = app;
        this.header = header;
        JsonPrinter.TAG = this.LOG_TAG;
    }

    public String getLogTag() {
        return this.LOG_TAG;
    }

    public boolean isLog() {
        return this.LOG;
    }
}
