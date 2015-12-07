package com.hadlink.easynetsample.datamanager.net;


import com.hadlink.easynetsample.TestResponse;
import com.hadlink.easynetsample.datamanager.bean.Certificaty;
import com.hadlink.easynetsample.datamanager.bean.WaitingAskBean;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseBeanResponse;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseListResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * api概览类
 */
public interface ApiOverview {


    /**
     * 等待回答列表
     */
    @GET("question/getWaitReplyList")
    Call<BaseListResponse<WaitingAskBean>> getWaitReplyList(@Query("expertID") int expertID,
                                                            @Query("pageNum") int pageNum,
                                                            @Query  ("numPerPage") int numPerPage);
    @GET("question/getWaitReplyList")
    Call<TestResponse> getWaitReplyList1(@Query("expertID") int expertID,
                                                            @Query("pageNum") int pageNum,
                                                            @Query  ("numPerPage") int numPerPage);

    /**
     * 等待回答列表
     */
    @GET("expertUser/selectCertificates")
    Call<BaseBeanResponse<Certificaty>> getCertificates(@Query("expertID") int expertID);


}
