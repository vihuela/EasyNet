package com.hadlink.easynet.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


class OkHttpUtils {
    private final static File RESPONSE_CACHE = NetUtils.netConfig.RESPONSE_CACHE;
    private final static int RESPONSE_CACHE_SIZE = NetUtils.netConfig.RESPONSE_CACHE_SIZE;
    private final static int HTTP_CONNECT_TIMEOUT = NetUtils.netConfig.HTTP_CONNECT_TIMEOUT;
    private final static int HTTP_READ_TIMEOUT = NetUtils.netConfig.HTTP_READ_TIMEOUT;
    private final static int MAX_CACHE_AGE = NetUtils.netConfig.MAX_CACHE_AGE;
    private static OkHttpClient singleton;

    static OkHttpClient getInstance(final Context context) {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {

                    singleton = new OkHttpClient().newBuilder()
                            .cache(new Cache(RESPONSE_CACHE != null ? RESPONSE_CACHE : context.getCacheDir(), RESPONSE_CACHE_SIZE))
                            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                            .addInterceptor(new LoggingInterceptor())
                            .addNetworkInterceptor(new HeaderInterceptor())
                            .build();
                }
            }
        }
        return singleton;
    }


    /**
     * in ok3 unUseless ..
     */
    private static class CacheInterceptor implements Interceptor {

        @Override public Response intercept(Chain chain) throws IOException {
            final Request.Builder builder = chain.request().newBuilder();


            builder.cacheControl(CacheControl.FORCE_NETWORK);

            return chain.proceed(builder.build());
        }
    }

    private static class HeaderInterceptor implements Interceptor {

        @Override public Response intercept(Chain chain) throws IOException {
            final Request.Builder builder = chain.request().newBuilder();

            if (NetUtils.netConfig.header != null) {
                for (Map.Entry<String, String> entry : NetUtils.netConfig.header.entrySet()) {
                    if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                        builder.addHeader(entry.getKey(), entry.getValue()).build();
                    }
                }
            }
            if (MAX_CACHE_AGE > 0) {
                builder.removeHeader("Pragma")
                        .header("Cache-Control", String.format("max-age=%d", MAX_CACHE_AGE));

            }

            return chain.proceed(builder.build());
        }
    }

    private static class LoggingInterceptor implements Interceptor {

        private String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            if (NetUtils.netConfig.LOG) {
                long t1 = System.nanoTime();
                String TAG = NetUtils.netConfig.LOG_TAG;

                Request request = chain.request();
                String param = "post".equalsIgnoreCase(request.method()) ? "---REQ:" + "\n" + "       " + bodyToString(request) + "\n" : "";
                String beautyPrint;
                Response response;
                try {
                    response = chain.proceed(request);
                } catch (IOException e) {
                    beautyPrint = "--------------REQUEST START------------" + "\n"
                            + String.format("---URL:%s %s Access Error", request.url(), request.method()) + "\n"
                            + param
                            + String.format("---Res:%s", !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName()) + "\n";
                    Log.e(TAG, beautyPrint);
                    Log.e(TAG, "--------------REQUEST END--------------");
                    throw new IOException(e);
                }

                String bodyString = response.body().string();

                long t2 = System.nanoTime();
                if (bodyString.startsWith("{") || bodyString.startsWith("[")) {
                    beautyPrint = "--------------REQUEST START------------" + "\n"
                            + String.format("---URL:%s %s in %.1fms", request.url(), request.method(), (t2 - t1) / 1e6d) + "\n"
                            + param
                            + String.format("---RES:%s %d %s", response.protocol().toString(), response.code(), response.message()) + "\n";
                    Log.d(TAG, beautyPrint);
                    if (NetUtils.netConfig.PRINT_BODY)
                        JsonPrinter.json(bodyString);
                    Log.d(TAG, "--------------REQUEST END--------------");
                } else {
                    beautyPrint = "--------------REQUEST START------------" + "\n"
                            + String.format("---URL:%s %s in %.1fms", request.url(), request.method(), (t2 - t1) / 1e6d) + "\n"
                            + param
                            + String.format("---RES:%s %d %s", response.protocol().toString(), response.code(), response.message()) + "\n"
                            + bodyString + "\n"
                            + "--------------REQUEST END--------------";
                    Log.d(TAG, beautyPrint);
                }
                return response.newBuilder()
                        .body(ResponseBody.create(response.body().contentType(), bodyString))
                        .build();
            }
            return chain.proceed(chain.request().newBuilder().build());
        }
    }
}
