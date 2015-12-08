package com.hadlink.easynetsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hadlink.easynetsample.datamanager.bean.Joke;
import com.hadlink.easynetsample.datamanager.bean.News;
import com.hadlink.easynetsample.datamanager.net.MyNet;
import com.hadlink.easynetsample.datamanager.net.MyNetCallBack;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseList_1Response;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseList_2Response;
import com.hadlink.easynetsample.datamanager.net.response.NewsResponse;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        before();

        after1();

        after2();

    }

    private void before() {
        Call<NewsResponse> originResponseCall = MyNet.get().getNews("普京", "20f453107e7739c9a363edb7507bd0ed");
        originResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override public void onResponse(Response<NewsResponse> response, Retrofit retrofit) {
                if (response.body() != null && response.body().error_code == 0) {
                    /**
                     * server api design is not a good return data redundancy may exist, we need to be screened
                     * 服务端api设计可能不良好，存在返回数据之后需要重新拼装筛选
                     */
                    List<NewsResponse.ResultEntity> result = response.body().result;
                    List<News> newsList = new ArrayList<>();
                    for (NewsResponse.ResultEntity entity : result) {
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

            @Override public void onFailure(Throwable t) {
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

    private void after1() {
        Call<BaseList_1Response<News>> responseCall = MyNet.get().getNews_("普京", "20f453107e7739c9a363edb7507bd0ed");
        responseCall.enqueue(new MyNetCallBack<BaseList_1Response<News>>() {


            @Override public void onSuccess(BaseList_1Response<News> newsBaseList1Response) {
                /**
                 * Direct access,NO need to be screened
                 * 直接拿到想要的bean对象，不需要筛选
                 */
                List<News> result = newsBaseList1Response.getResult();
                //
            }

            @Override public void onDispatchError(Error error, Object message) {
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

    private void after2() {
        Call<BaseList_2Response<Joke>> responseCall = MyNet.get().getJokes();
        responseCall.enqueue(new MyNetCallBack<BaseList_2Response<Joke>>() {
            @Override public void onDispatchError(Error error, Object message) {
                super.onDispatchError(error, message);
            }

            @Override public void onSuccess(BaseList_2Response<Joke> jokeBaseList_2Response) {

                /**
                 * Direct access.
                 */
                List<Joke> result = jokeBaseList_2Response.getResult();
            }
        });
    }

}
