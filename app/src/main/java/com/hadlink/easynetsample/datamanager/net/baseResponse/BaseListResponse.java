package com.hadlink.easynetsample.datamanager.net.baseResponse;


import com.hadlink.easynet.impl.CommonResponse;

import java.util.List;

/**
 * 适合列表加载
 * @param <T> 列表item bean
 */
public class BaseListResponse<T> implements CommonResponse<List<T>> {

    public long code;
    public String message;
    public DataEntity data;

    @Override public List<T> getResult() {
        return data.pageData;
    }

    @Override public void setResult(List<T> ts) {
        this.data.pageData = ts;
    }

    /**
     * 大部分接口返回200为有效数据
     */
    @Override public boolean isValid() {
        return code == 200;
    }

    public  class DataEntity {
        /**
         * 列表通用数据----------------
         */
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
        /**
         * 具体列表
         */
        public List<T> pageData;
    }

}
