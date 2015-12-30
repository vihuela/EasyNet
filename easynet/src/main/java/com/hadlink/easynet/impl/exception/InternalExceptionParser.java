package com.hadlink.easynet.impl.exception;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.hadlink.easynet.impl.CommonDispatchRequest;

public class InternalExceptionParser extends ExceptionParser {


    @Override protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();

            if (e instanceof NumberFormatException || e instanceof JsonSyntaxException) {
                handler.onHandler(CommonDispatchRequest.Error.Internal, s);
                return true;
            }
        }
        return false;
    }
}
