package com.hadlink.easynet.util;

import com.hadlink.easynet.impl.DispatchRequestImpl;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class NetUtils {
    public static NetConfig netConfig ;
    public static void setNetConfig(NetConfig netConf){
        NetUtils.netConfig = netConf;
    }
    public static <T> T createApi(Class<T> cls, String host) {
        return RetrofitUtils.createApi(netConfig.app, cls, host);
    }

    /**
     * retain run mainThread ob
     */
    public static <T> Observable<T> getMainThreadObservable(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * call noNeed non200 check
     */
    public static abstract class callBack<T> extends DispatchRequestImpl<T> {

        public callBack() {
            super();
        }
    }


}
