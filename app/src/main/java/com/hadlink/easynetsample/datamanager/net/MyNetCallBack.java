package com.hadlink.easynetsample.datamanager.net;

import android.widget.Toast;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.easynetsample.app.App;


/**
 * 接受网络层发回的错误(具体处理可使用eventBus post，或者广播 etc)
 *
 * @param <T> 原始bean
 */
public abstract class MyNetCallBack<T> extends NetUtils.callBack<T> {


    public MyNetCallBack(String eventTag) {
        super(eventTag);
    }

    public MyNetCallBack() {
        super(null);
    }

    @Override public void onDispatchError(Error error, ErrorInfo e) {
        switch (error) {
            case Internal:
                Toast(e.getObject().toString());
                break;
            case Invalid:
                T result = (T) e.getObject();
                /**
                 * 当数据无效时候你做的处理
                 */
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
    }

    public void Toast(String msg) {
        Toast.makeText(App.getInstance(), msg, Toast.LENGTH_LONG).show();
    }
}
