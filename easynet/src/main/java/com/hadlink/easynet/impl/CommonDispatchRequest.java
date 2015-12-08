package com.hadlink.easynet.impl;


public interface CommonDispatchRequest<T> {
    String IO_EXCEPTION = "Canceled";
    String SOCKET_EXCEPTION = "Socket closed";

    void onDispatchError(Error error, Object message);

    void onDispatchSuccess(T t);

    enum Error {
        NetWork,//net not response or timeout etc
        Internal,//json parse error etc
        Server,//404.500 ..etc
        UnKnow,
        Invalid//request success but no need
    }
}
