package com.hadlink.easynet.impl;


public interface CommonResponse<T> {

    /**
     * 接口返回的有效数据方法
     */
    T getResult();

    /**
     * 设置具体是什么值是接口返回的有效数据
     */
    void setResult(T t);

    /**
     * 判断接口返回的数据是否有效
     */
    boolean isValid();
}
