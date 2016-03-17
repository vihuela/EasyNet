package com.hadlink.easynetsample.datamanager.net.response;

import com.hadlink.easynet.impl.CommonResponse;

import java.util.List;

/**
 * 针对接口格式和需要的数据在bean内部作一次数据判断，泛型是你需要的数据
 */
public class NewsResponseUpdate implements CommonResponse<List<NewsResponseUpdate.ResultEntity>> {


    public String reason;
    public int error_code;

    public List<ResultEntity> result;

    @Override public List<ResultEntity> getResult() {
        return result;
    }

    @Override public void setResult(List<ResultEntity> resultEntities) {
        this.result = resultEntities;
    }


    /**
     * 指定返回正确格式
     */
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
