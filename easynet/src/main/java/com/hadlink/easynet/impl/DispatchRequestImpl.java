package com.hadlink.easynet.impl;

import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;
import com.hadlink.easynet.impl.exception.ExceptionParser;
import com.hadlink.easynet.impl.exception.InternalExceptionParser;
import com.hadlink.easynet.impl.exception.NetExceptionParser;
import com.hadlink.easynet.impl.exception.ServerExceptionParser;
import com.hadlink.easynet.impl.exception.UnknowExceptionParser;
import com.hadlink.easynet.util.GsonUtils;
import com.hadlink.easynet.util.NetUtils;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscriber;

/**
 * compose Call and Observable callBack
 */
public abstract class DispatchRequestImpl<T> extends Subscriber<T> implements Callback<T>, CommonDispatchRequest<T> {

    private Object result;

    public DispatchRequestImpl() {
    }

    protected Object getResult() {
        return result;
    }

    /**
     * rx
     */
    @Override public final void onCompleted() {

    }

    @Override public final void onError(Throwable e) {

        NetExceptionParser firstParser = new NetExceptionParser();
        ServerExceptionParser secondParser = new ServerExceptionParser();
        InternalExceptionParser thirdParser = new InternalExceptionParser();
        UnknowExceptionParser fourthParser = new UnknowExceptionParser();

        fourthParser.setNextParser(null);
        thirdParser.setNextParser(fourthParser);
        secondParser.setNextParser(thirdParser);
        firstParser.setNextParser(secondParser);

        firstParser.handleException(e, new ExceptionParser.IHandler() {
            @Override public void onHandler(Error error, String message) {
                onDispatchError(error, message);
            }
        });
    }


    @Override public final void onNext(T t) {
        onDispatchSuccess(t);
    }

    /**
     * call
     */
    @Override public final void onResponse(Response<T> response, Retrofit retrofit) {
        T body = response.body();
        if (body == null) {
            onDispatchError(Error.Server, response.raw().code() + "," + response.raw().message());
        } else {
            onDispatchSuccess(body);
        }
    }

    @Override public final void onFailure(Throwable t) {

        onError(t);
    }

    @Override public abstract void onDispatchError(Error error, Object message);

    @SuppressWarnings("all")
    @Override public final void onDispatchSuccess(T t) {
        this.result = t;

        if (t != null && t instanceof CommonResponse) {
            CommonResponse c = (CommonResponse) t;
            if (c.isValid()) {
                /**
                 *  have list
                 */
                if (c.getResult() != null) {
                    if (List.class.isInstance(c.getResult())) {
                        try {
                            ParameterizedType typeCallOROb = (ParameterizedType) this.getClass().getGenericSuperclass();
                            ParameterizedType typeBeanORList = (ParameterizedType) typeCallOROb.getActualTypeArguments()[0];
                            /**
                             * two level gennic
                             */
                            Class<?> clazzResult = (Class) (typeBeanORList.getActualTypeArguments()[0]);
                            List l = new ArrayList();
                            List<LinkedTreeMap> list = (List<LinkedTreeMap>) c.getResult();
                            for (LinkedTreeMap map : list) {
                                JSONObject ob = new JSONObject(map);
                                l.add(GsonUtils.INSTANCE.get().fromJson(ob.toString(), clazzResult));
                            }
                            c.setResult(l);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                onSuccess(t);
            } else {
                onDispatchError(Error.Invalid, t);
            }
        } else if (t != null && t.getClass() != null) {
            printLog("check " + t.getClass().getSimpleName() + " whether inheritance CommonResponse Please");
        } else {
            //t is null
            onDispatchError(Error.UnKnow, "response bean is null");
        }
    }

    private void printLog(String s) {
        if (NetUtils.netConfig.isLog())
            Log.e(NetUtils.netConfig.getLogTag(), s);
    }

    public abstract void onSuccess(T t);

}
