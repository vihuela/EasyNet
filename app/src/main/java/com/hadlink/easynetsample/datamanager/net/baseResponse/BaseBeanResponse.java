package com.hadlink.easynetsample.datamanager.net.baseResponse;


import com.hadlink.easynet.impl.CommonResponse;

/**
 * 适合常规费列表接口
 * @param <T>
 */
public class BaseBeanResponse<T> implements CommonResponse<T> {

    public T data;
    public long code;
    public String message;



    @Override public T getResult() {
        return data;
    }

    @Override public void setResult(T t) {
        data = t;
    }

    @Override public boolean isValid() {
        return code==200;
    }


}
