package com.hadlink.easynet.impl;


public interface CommonResponse<T> {

    /**
     * restful valid data
     */
    T getResult();

    /**
     * Designation type of valid data
     */
    void setResult(T t);

    /**
     * check data is it effective
     */
    boolean isValid();
}
