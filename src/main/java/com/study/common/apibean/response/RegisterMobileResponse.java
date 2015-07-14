package com.study.common.apibean.response;

/**
 * Created by huichao on 2015/7/13.
 */
public class RegisterMobileResponse {

    private String code;
    private String message;
    private Object content;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
