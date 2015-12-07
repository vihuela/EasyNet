package com.hadlink.easynetsample.datamanager.net;

import android.widget.Toast;

import com.hadlink.easynet.util.NetUtils;
import com.hadlink.easynetsample.app.App;


/**
 * 接受网络层发回的错误(具体处理可使用eventBus post，或者广播 etc)
 * @param <T> 原始bean
 */
public abstract class MyNetCallBack<T> extends NetUtils.callBack<T> {

    @Override public void onDispatchError(Error error, Object message) {
        switch (error){
            case Internal:
                Toast(message.toString());
                break;
            case Invalid:
                T result = (T) message;
                /**
                 * 当数据无效时候你做的处理
                 */
                break;
            case NetWork:
                Toast(message.toString());
                break;
            case Server:
                Toast(message.toString());
                break;
            case UnKnow:
                Toast(message.toString());
                break;
        }
    }
    public void Toast(String msg){
        Toast.makeText(App.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
}
