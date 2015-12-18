package com.hadlink.easynetsample.datamanager.net.response;

import com.hadlink.easynet.impl.CommonResponse;

import java.util.List;

/**
 * 以下为GsonFormat生成的bean
 */
public class NewsResponseUpdate implements CommonResponse {


    public String reason;
    public int error_code;

    public List<ResultEntity> result;

    @Override public Object getResult() {
        return null;
    }

    @Override public void setResult(Object o) {

    }

    @Override public boolean isValid() {
        return error_code == 0;
    }

    public static class ResultEntity {
        public String title;
        public String content;
        public String img_width;
        public String full_title;
        public String pdate;
        public String src;
        public String img_length;
        public String img;
        public String url;
        public String pdate_src;
    }
}
