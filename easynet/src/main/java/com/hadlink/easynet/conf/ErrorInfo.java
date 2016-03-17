package com.hadlink.easynet.conf;

import com.hadlink.easynet.impl.CommonDispatchRequest;

public class ErrorInfo {
    private String eventTag;
    private CommonDispatchRequest.Error errorType;
    private Object object;

    public ErrorInfo() {
    }

    public ErrorInfo(String eventTag, Object object, CommonDispatchRequest.Error errorType) {
        this.eventTag = eventTag;
        this.object = object;
        this.errorType = errorType;
    }

    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    public <T extends Object> T getObject() {
        return (T) object;
    }

    public void setObject(Object obj) {
        this.object = obj;
    }

    public CommonDispatchRequest.Error getError() {
        return errorType;
    }

    public void setError(CommonDispatchRequest.Error error) {
        this.errorType = error;
    }
}
