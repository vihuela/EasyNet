package com.hadlink.easynetsample.datamanager.net;


import com.hadlink.easynet.util.NetUtils;
import com.hadlink.easynetsample.conf.C;


public class MyNet {
    public static ApiOverview get() {
        return NetUtils.createApi(ApiOverview.class, C.Host.host);
    }
}
