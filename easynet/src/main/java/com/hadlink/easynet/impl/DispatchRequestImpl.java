package com.hadlink.easynet.impl;

import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;
import com.hadlink.easynet.conf.ErrorInfo;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

/**
 * compose Call and Observable callBack
 */
public abstract class DispatchRequestImpl<T> extends Subscriber<T> implements Callback<T>, CommonDispatchRequest<T> {

    public String eventTag;

    public DispatchRequestImpl(String eventTag) {
        this.eventTag = eventTag;
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
                onDispatchError(error, new ErrorInfo(eventTag, message, error));
            }
        });
    }


    @Override public final void onNext(T t) {
        onDispatchSuccess(t);
    }

    /**
     * call
     */
    @Override public final void onResponse(Call<T> call, Response<T> response) {
        T body = response.body();
        if (body == null) {
            String message = response.raw().code() + "," + response.raw().message();
            onDispatchError(Error.Server, new ErrorInfo(eventTag, message, Error.Server));
        } else {
            onDispatchSuccess(body);
        }
    }

    @Override public final void onFailure(Call<T> call, Throwable t) {
        onError(t);
    }

    @Override public abstract void onDispatchError(Error error, ErrorInfo message);

    @SuppressWarnings("all")
    @Override public final void onDispatchSuccess(T t) {

        if (t != null && t instanceof CommonResponse) {
            CommonResponse c = (CommonResponse) t;
            if (c.isValid()) {
                /**
                 *  have list
                 */
                if (c.getResult() != null && List.class.isInstance(c.getResult())) {
                    /**
                     * gson bug
                     */
                    if (((List) c.getResult()).size() > 0) {
                        Object first = ((List) c.getResult()).get(0);
                        if (LinkedTreeMap.class.isInstance(first)) {
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
                }
                onSuccess(t);
            } else {
                onDispatchError(Error.Invalid, new ErrorInfo(eventTag, t, Error.Invalid));
            }
        } else if (t != null && t.getClass() != null) {
            printLog("check " + t.getClass().getSimpleName() + " whether inheritance CommonResponse Please");
        } else {
            //t is null
            onDispatchError(Error.UnKnow, new ErrorInfo(eventTag, "response bean is null", Error.UnKnow));
        }
    }

    private void printLog(String s) {
        if (NetUtils.netConfig.isLog())
            Log.e(NetUtils.netConfig.getLogTag(), s);
    }

    public abstract void onSuccess(T t);

}
