package com.hadlink.easynetsample.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.ArrayMap;

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
        ArrayMap<String, String> header = new ArrayMap<>();
        header.put("User-Agent", "android");
        /**
         * net config
         */
        final NetConfig netConfig = new NetConfigBuilder()
                .context(this)
                .log(true)
                .logTag("you_tag_name")
                .printResponseBody(true)
                .header(header)
                .createNetConfig();

        NetUtils.setNetConfig(netConfig);
    }
}
