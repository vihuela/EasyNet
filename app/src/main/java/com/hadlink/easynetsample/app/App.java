package com.hadlink.easynetsample.app;

import android.app.Application;
import android.content.Context;

import com.hadlink.easynet.conf.NetConfigBuilder;
import com.hadlink.easynet.util.NetConfig;
import com.hadlink.easynet.util.NetUtils;


public class App extends Application {
    public static App instance;

    public static Context getInstance() {
        return instance;
    }

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        /**
         * net config
         */
        final NetConfig netConfig = new NetConfigBuilder()
                .appContext(this)
                .responseCacheDir("you_cache_dir_name")
                .log(true)
                .logTag("you_tag_name")
                .printResponseBody(true)
                .createNetConfig();

        NetUtils.setNetConfig(netConfig);
    }
}
