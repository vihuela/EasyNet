package com.hadlink.easynetsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hadlink.easynetsample.datamanager.bean.WaitingAskBean;
import com.hadlink.easynetsample.datamanager.net.MyNet;
import com.hadlink.easynetsample.datamanager.net.MyNetCallBack;
import com.hadlink.easynetsample.datamanager.net.baseResponse.BaseListResponse;

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

        after();

    }

    private void before() {
        final Call<TestResponse> waitReplyList = MyNet.get().getWaitReplyList1(107, 1, 20);
        waitReplyList.enqueue(new Callback<TestResponse>() {
            @Override public void onResponse(Response<TestResponse> response, Retrofit retrofit) {
                System.out.println();
            }

            @Override public void onFailure(Throwable t) {

            }
        });

    }

    private void after() {
        final Call<BaseListResponse<WaitingAskBean>> waitReplyList = MyNet.get().getWaitReplyList(107, 1, 20);
        waitReplyList.enqueue(new MyNetCallBack<BaseListResponse<WaitingAskBean>>() {
            @Override public void onSuccess(BaseListResponse<WaitingAskBean> waitingAskBeanBaseListResponse) {
                System.out.println();//debug check
            }
        });

    }

}
