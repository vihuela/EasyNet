package com.hadlink.easynetsample.datamanager.net.baseResponse;


import com.hadlink.easynet.impl.CommonResponse;

import java.util.List;

/**
 * 适合列表加载
 *
 * @param <T> 列表item bean
 */
public class BaseList_2Response<T> implements CommonResponse<List<T>> {

    public String reason;

    public int error_code;

    public ResultEntity result;

    @Override public List<T> getResult() {
        return result.data;
    }

    @Override public void setResult(List<T> ts) {
        this.result.data = ts;
    }

    @Override public boolean isValid() {
        return error_code == 0;
    }

    public class ResultEntity {
        /**
         * content : 女生分手的原因有两个，
         * 一个是：闺蜜看不上。另一个是：闺蜜看上了。
         * hashId : 607ce18b4bed0d7b0012b66ed201fb08
         * unixtime : 1418815439
         * updatetime : 2014-12-17 19:23:59
         */

        public List<T> data;

    }


}
