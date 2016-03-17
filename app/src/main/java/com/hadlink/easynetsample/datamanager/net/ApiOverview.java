package com.hadlink.easynetsample.datamanager.net;


import com.hadlink.easynetsample.datamanager.bean.ImageDetail;
import com.hadlink.easynetsample.datamanager.bean.Joke;
import com.hadlink.easynetsample.datamanager.bean.News;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseList_1Response;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseList_2Response;
import com.hadlink.easynetsample.datamanager.net.response.ImageListResponse;
import com.hadlink.easynetsample.datamanager.net.response.NewsResponseOrigin;
import com.hadlink.easynetsample.datamanager.net.response.NewsResponseUpdate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * api概览类
 */
public interface ApiOverview {


    @GET("onebox/news/query")
    Call<NewsResponseOrigin> getNewsOrigin(@Query("q") String content, @Query("key") String key);

    @GET("question/getQuestionDetails?userID=171410&code=0&questionID=520120")//404 http://usrapi.imchehu.cn//
    Call<NewsResponseUpdate> getNewsUpdate(@Query("q") String content, @Query("key") String key);


    @GET("onebox/news/query")
    Call<BaseList_1Response<News>> getNews_(@Query("q") String content, @Query("key") String key);

    @GET("http://japi.juhe.cn/joke/content/list.from?sort=desc&page=1&pagesize=10&time=1418816972&key=d4ac32344201cd2e005c966e08271702")
    Call<BaseList_2Response<Joke>> getJokes();

    /**
     * 图片列表
     */
    @GET("http://image.baidu.com/data/imgs?tag=全部&rn="+20+"&from=1") Observable<ImageListResponse<ImageDetail>> getImageList(@Query("col") String classify,@Query("pn") int startIndex);


}
