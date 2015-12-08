package com.hadlink.easynet.util;

import android.content.Context;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * retain retrofit singleton
 */
public class RetrofitUtils {
    /**
     * baseUrl end add "/"
     * path end no add "/"
     */

    static Retrofit singleton;


    static <T> T createApi(Context context, Class<T> clazz, String host) {
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    singleton = new Retrofit.Builder()
                            .baseUrl(host)
                            .addConverterFactory(GsonConverterFactory.create(GsonUtils.INSTANCE.get()))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(OkHttpUtils.getInstance(context))
                            .build();
                }
            }

        }
        return singleton.create(clazz);
    }

}
