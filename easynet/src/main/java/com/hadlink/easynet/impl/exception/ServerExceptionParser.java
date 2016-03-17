package com.hadlink.easynet.impl.exception;

import android.text.TextUtils;

import com.hadlink.easynet.impl.CommonDispatchRequest;

import retrofit2.adapter.rxjava.HttpException;


public class ServerExceptionParser extends ExceptionParser {

    @Override protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();

            if (e instanceof HttpException) {
                handler.onHandler(CommonDispatchRequest.Error.Server, s);
                return true;
            }
        }
        return false;
    }
}
