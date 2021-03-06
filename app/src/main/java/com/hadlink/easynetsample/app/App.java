package com.hadlink.easynetsample.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.hadlink.easynet.conf.NetConfigBuilder;
import com.hadlink.easynet.util.NetConfig;
import com.hadlink.easynet.util.NetUtils;

import java.io.File;
import java.util.HashMap;


public class App extends Application {
    public static App instance;

    public static Context getInstance() {
        return instance;
    }

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        HashMap<String, String> header = new HashMap<>();
        header.put("visit_from", "android");
        /**
         * net config
         */
        final NetConfig netConfig = new NetConfigBuilder()
                .context(this)
                .log(true)
                .readTimeout(2000)
                .logTag("you_tag_name")
                .responseCacheDir(new File(Environment.getExternalStorageDirectory(),"easyNetResponse"))
                /*.maxCacheAge(3600 * 12)*/
                .printResponseBody(true)
                .header(header)
                .createNetConfig();

        NetUtils.setNetConfig(netConfig);
    }
}
