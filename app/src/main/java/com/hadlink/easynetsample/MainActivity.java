package com.hadlink.easynetsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.easynetsample.datamanager.bean.ImageDetail;
import com.hadlink.easynetsample.datamanager.bean.Joke;
import com.hadlink.easynetsample.datamanager.bean.News;
import com.hadlink.easynetsample.datamanager.net.MyNet;
import com.hadlink.easynetsample.datamanager.net.MyNetCallBack;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseList_1Response;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseList_2Response;
import com.hadlink.easynetsample.datamanager.net.response.CacheBeanResponse;
import com.hadlink.easynetsample.datamanager.net.response.HistroyResponse;
import com.hadlink.easynetsample.datamanager.net.response.ImageListResponse;
import com.hadlink.easynetsample.datamanager.net.response.NewsResponseOrigin;
import com.hadlink.easynetsample.datamanager.net.response.NewsResponseUpdate;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*before1();*/
        /*after1();*/

    }

    public void exec1(View view) {
        cacheTest2();
    }

    public void exec2(View view) {
        cacheTest3();
    }

    private void cacheTest3() {
        Call<HistroyResponse> histroyList = MyNet.get().getHistroyList();
        histroyList.enqueue(new MyNetCallBack<HistroyResponse>() {
            @Override
            public void onSuccess(HistroyResponse histroyResponse) {

            }
        });
    }

    private void cacheTest2() {
        Call<CacheBeanResponse> cacheTest1 = MyNet.get().getCacheTest1();
        cacheTest1.enqueue(new MyNetCallBack<CacheBeanResponse>() {
            @Override
            public void onSuccess(CacheBeanResponse cacheBeanResponse) {

            }
        });
    }

    private void cacheTest1() {
        Observable<CacheBeanResponse> cacheTest = MyNet.get().getCacheTest();
        cacheTest
                .compose(NetUtils.<CacheBeanResponse>applySchedulers())
                .subscribe(new MyNetCallBack<CacheBeanResponse>() {
                    @Override
                    public void onSuccess(CacheBeanResponse cacheBeanResponse) {

                    }
                });
    }


    /**
     * retrofit2原始用法
     */
    private void before1() {
        Call<NewsResponseOrigin> originResponseCall = MyNet.get().getNewsOrigin("普京", "20f453107e7739c9a363edb7507bd0ed");
        originResponseCall.enqueue(new Callback<NewsResponseOrigin>() {
            @Override
            public void onResponse(Call<NewsResponseOrigin> call, Response<NewsResponseOrigin> response) {
                //数据code正确时
                if (response.body() != null && response.body().error_code == 0) {
                    /**
                     * server api design is not a good return data redundancy may exist, we need to be screened
                     * 服务端api设计可能不良好，存在返回数据之后需要重新拼装筛选
                     */
                    List<NewsResponseOrigin.ResultEntity> result = response.body().result;
                    List<News> newsList = new ArrayList<>();
                    for (NewsResponseOrigin.ResultEntity entity : result) {
                        News news = new News();
                        news.content = entity.content;
                        news.full_title = entity.full_title;
                        news.img = entity.img;
                        //...
                        newsList.add(news);
                    }
                    //fill list to recycler/listView

                    /**
                     * There may be a whole lot like this App interface, that the above code should write again?
                     * 可能整个App存在很多这样子的接口，那不是以上代码都需要走一遍？
                     */
                }
            }

            @Override
            public void onFailure(Call<NewsResponseOrigin> call, Throwable t) {
                /**
                 * You need to judge for themselves what type of error
                 * 在retrofit2默认错误回调中，你需要自己去处理分析错误类型
                 */
                if (t != null) {
                    if (t instanceof UnknownHostException || (t.getCause() != null && t.getCause() instanceof UnknownHostException)) {
                        //net error
                    } else {
                        //json parse error
                    }
                }
            }
        });
    }

    /**
     * 针对原始改进之后的用法
     */
    private void after1() {
        Call<NewsResponseUpdate> responseUpdate = MyNet.get().getNewsUpdate("普京", "20f453107e7739c9a363edb7507bd0ed");
        responseUpdate.enqueue(new MyNetCallBack<NewsResponseUpdate>("eventTag") {
            @Override
            public void onSuccess(NewsResponseUpdate responseUpdate) {
                //仅有效数据时才回调
                List<NewsResponseUpdate.ResultEntity> result = responseUpdate.getResult();
            }

            @Override
            public void onDispatchError(Error error, ErrorInfo e) {
                switch (error) {
                    case Internal:
                        Toast(e.getObject().toString());
                        break;
                    case Invalid:
                        /**
                         * 有返回数据但是是无效的
                         */
                        NewsResponseUpdate result = e.getObject();

                        break;
                    case NetWork:
                        Toast(e.getObject().toString());
                        break;
                    case Server:
                        Toast(e.getObject().toString());
                        break;
                    case UnKnow:
                        Toast(e.getObject().toString());
                        break;
                }
                /**
                 * 可以统一底层事件tag分发，也可以在这里具体判断
                 */
                super.onDispatchError(error, e);
            }
        });
    }

    /**
     * 更清晰的错误分发用法
     */
    private void extra1() {

        Observable<ImageListResponse<ImageDetail>> imageList = MyNet.get().getImageList("美女", 1);
        NetUtils.getMainThreadObservable(imageList)
                .subscribe(new MyNetCallBack<ImageListResponse<ImageDetail>>("abc") {

                    @Override
                    public void onSuccess(ImageListResponse<ImageDetail> imageDetailImageListResponse) {
                        List<ImageDetail> result = imageDetailImageListResponse.getResult();
                    }

                    @Override
                    public void onDispatchError(Error error, ErrorInfo message) {
                        super.onDispatchError(error, message);
                    }
                });

    }

    /**
     * 适合多个有规律的接口抽取baseResponse用法
     */
    private void extra2() {
        Call<BaseList_1Response<News>> responseCall = MyNet.get().getNews_("普京", "20f453107e7739c9a363edb7507bd0ed");
        responseCall.enqueue(new MyNetCallBack<BaseList_1Response<News>>() {


            @Override
            public void onSuccess(BaseList_1Response<News> newsBaseList1Response) {
                /**
                 * Direct access,NO need to be screened
                 * 直接拿到想要的bean对象，不需要筛选
                 */
                List<News> result = newsBaseList1Response.getResult();
                //
            }

            @Override
            public void onDispatchError(Error error, ErrorInfo message) {
                super.onDispatchError(error, message);
                /**
                 * error is enum ，A clear understanding of the wrong type
                 * error是个枚举类型，可以自行判断
                 */
                switch (error) {
                    case Internal:
                        break;
                    case NetWork:
                        break;
                }
            }
        });
    }

    private void extra3() {
        Call<BaseList_2Response<Joke>> responseCall = MyNet.get().getJokes();
        responseCall.enqueue(new MyNetCallBack<BaseList_2Response<Joke>>() {
            @Override
            public void onDispatchError(Error error, ErrorInfo message) {
                super.onDispatchError(error, message);
            }

            @Override
            public void onSuccess(BaseList_2Response<Joke> jokeBaseList_2Response) {

                /**
                 * Direct access.
                 */
                List<Joke> result = jokeBaseList_2Response.getResult();
            }
        });
    }


}
