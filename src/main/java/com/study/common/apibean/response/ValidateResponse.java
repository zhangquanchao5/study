package com.study.common.apibean.response;

/**
 * Created by huichao on 2015/7/24.
 */
public class ValidateResponse {
    private boolean result;
    private Object info;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
