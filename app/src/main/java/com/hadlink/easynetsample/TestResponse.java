package com.hadlink.easynetsample;

import java.util.List;

/**
 * Created by lyao on 2015/12/7.
 */
public class TestResponse {

    public long code;
    public String message;
    public DataEntity data;

    public static class DataEntity {
        public int dataTotal;
        public boolean nextPage;
        public int pageNo;
        public int pageNumEnd;
        public int pageNumStart;
        public int pageSize;
        public int pageTotal;
        public boolean prevPage;
        public int showPageNum;
        public int startOfPage;
        public List<SubDataEntity> pageData;
    }

    public static class SubDataEntity {

        public int pageTotal;//总页数
        public int dataTotal;//数据总条数
        public boolean isFirst;//是否第一个条目，用于显示条目数布局
        public String nickName;
        public String avatarUrl;
        public int awardScore;
        public String createTime;
        public int gender;
        public int msgCount;
        public String questionContent;
        public int questionID;
        public String brandName;
        public String tagName;
        public int userID;
    }

}
