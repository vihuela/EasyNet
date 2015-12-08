package com.hadlink.easynetsample.datamanager.net.response;

import java.util.List;

public class HotResponse {


    public int error_code;
    public String reason;
    public ResultEntity result;

    public static class ResultEntity {
        /**
         * content : 女生分手的原因有两个，
         * 一个是：闺蜜看不上。另一个是：闺蜜看上了。
         * hashId : 607ce18b4bed0d7b0012b66ed201fb08
         * unixtime : 1418815439
         * updatetime : 2014-12-17 19:23:59
         */

        public List<DataEntity> data;

        public static class DataEntity {
            public String content;
            public String hashId;
            public int unixtime;
            public String updatetime;
        }
    }
}
