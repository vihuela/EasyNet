package com.hadlink.easynet.impl;

/**
 * 通用请求处理
 * @param <T>
 */
public interface CommonDispatchRequest<T> {
    String IO_EXCEPTION = "Canceled";
    String SOCKET_EXCEPTION = "Socket closed";

    void onDispatchError(Error error, Object message);

    void onDispatchSuccess(T t);

    enum Error {
        NetWork, //网络无连接错误，或者超时错误
        Internal,//JSON解析失败
        Server,//服务器404、500等错误
        UnKnow,//未知错误
        Invalid//数据无效错误
    }
}
