package com.hadlink.easynet.util;

import android.content.Context;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retain retrofit singleton
 */
class RetrofitUtils {
    /**
     * baseUrl end add "/"
     * path end no add "/"
     */

    private static Retrofit singleton;


    static <T> T createApi(Context context, Class<T> clazz, String host) {
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    Gson gson = NetUtils.netConfig.gson != null ? NetUtils.netConfig.gson : GsonUtils.INSTANCE.get();
                    singleton = new Retrofit.Builder()
                            .baseUrl(host)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(OkHttpUtils.getInstance(context))
                            .build();
                }
            }
        }
        return singleton.create(clazz);
    }

}
