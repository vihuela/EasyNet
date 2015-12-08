package com.hadlink.easynetsample.datamanager.net.response;

import java.util.List;

/**
 * 以下为GsonFormat生成的bean
 */
public class NewsResponse {



    public String reason;
    public int error_code;

    public List<ResultEntity> result;

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
