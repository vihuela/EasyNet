package com.hadlink.easynet.impl.exception;

import android.text.TextUtils;

import com.hadlink.easynet.impl.CommonDispatchRequest;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class NetExceptionParser extends ExceptionParser {


    @Override protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();
            if (CommonDispatchRequest.IO_EXCEPTION.equalsIgnoreCase(s) || CommonDispatchRequest.SOCKET_EXCEPTION.equalsIgnoreCase(s)) {
                //cancel request
                return true;
            }
            if (e instanceof UnknownHostException || e instanceof ConnectException || e instanceof SocketTimeoutException) {
                handler.onHandler(CommonDispatchRequest.Error.NetWork, s);
                return true;
            }
        }
        return false;
    }
}

