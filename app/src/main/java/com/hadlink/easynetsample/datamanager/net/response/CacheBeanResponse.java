package com.hadlink.easynetsample.datamanager.net.response;

import com.hadlink.easynet.impl.CommonResponse;
import com.hadlink.easynetsample.datamanager.bean.RequestInfo;

/**
 * @author YaoWeihui on 2016/5/9.
 */
public class CacheBeanResponse implements CommonResponse<RequestInfo> {
    public int code;
    public String msg;
    public RequestInfo data;

    @Override
    public RequestInfo getResult() {
        return data;
    }

    @Override
    public void setResult(RequestInfo requestInfo) {

    }

    @Override
    public boolean isValid() {
        return data != null;
    }
}
