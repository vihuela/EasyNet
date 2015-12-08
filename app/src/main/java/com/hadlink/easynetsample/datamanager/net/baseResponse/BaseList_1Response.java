package com.hadlink.easynetsample.datamanager.net.baseResponse;


import com.hadlink.easynet.impl.CommonResponse;

import java.util.List;

/**
 * 适合列表加载
 *
 * @param <T> 列表item bean
 */
public class BaseList_1Response<T> implements CommonResponse<List<T>> {

    public String reason;

    public int error_code;

    public List<T> result;

    @Override public List<T> getResult() {
        return result;
    }

    @Override public void setResult(List<T> ts) {
        this.result = ts;
    }


    @Override public boolean isValid() {
        return error_code == 0;
    }


}
