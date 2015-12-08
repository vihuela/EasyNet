package com.hadlink.easynet.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

/**
 * gson singleton
 */
public enum GsonUtils {
    INSTANCE;

    public Gson gson;

    GsonUtils() {
        GsonBuilder builder = new GsonBuilder();
        builder
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .enableComplexMapKeySerialization()
                        //                .registerTypeAdapter(Response.class, new MyCommonGsonAdapter());
                .registerTypeAdapter(Date.class, new DateTypeAdapter());
        gson = builder.create();
    }

    public Gson get() {
        return gson;
    }

}
