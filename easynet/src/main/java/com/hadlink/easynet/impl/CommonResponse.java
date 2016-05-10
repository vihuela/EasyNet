package com.hadlink.easynet.impl;


import java.io.Serializable;

public interface CommonResponse<T> extends Serializable{

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
