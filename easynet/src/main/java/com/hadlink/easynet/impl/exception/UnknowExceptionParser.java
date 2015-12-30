package com.hadlink.easynet.impl.exception;

import android.text.TextUtils;

import com.hadlink.easynet.impl.CommonDispatchRequest;

public class UnknowExceptionParser extends ExceptionParser {

    
    @Override protected boolean handler(Throwable e, IHandler handler) {
        String s = e != null ? !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName() : "unKnow";
        handler.onHandler(CommonDispatchRequest.Error.UnKnow, s);
        return true;
    }
}
