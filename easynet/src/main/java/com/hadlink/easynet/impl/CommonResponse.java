package com.hadlink.easynet.impl;


public interface CommonResponse<T> {

    /**
     * restful valid data [option]
     */
    T getResult();

    /**
     * Designation type of valid data [option]
     */
    void setResult(T t);

    /**
     * check data is it effective [must]
     */
    boolean isValid();
}
