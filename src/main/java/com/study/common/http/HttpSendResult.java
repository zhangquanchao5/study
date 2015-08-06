package com.study.common.http;

/**
 * Copyright lr.
 * User: Daocren
 * Date: 2008-6-24
 * Time: 10:08:45
 * Http请求结果
 */
public class HttpSendResult {
    private int statusCode;
    private String response;
    private Exception exception;

    /**
     * 获取状态码
     *
     * @return int status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 给状态码赋值
     *
     * @param statusCode the status code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 获取请求结果
     *
     * @return String response
     */
    public String getResponse() {
        return response;
    }

    /**
     * 给请求结果赋值
     *
     * @param response the response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * 获取异常
     *
     * @return Exception exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * 给异常赋值
     *
     * @param exception the exception
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * 判断返回是否成功
     *
     * @return boolean boolean
     */
    public boolean isSuccess() {
        return statusCode == 200;
    }

    /**
     * 返回结果字符串
     *
     * @return String
     */
    public String toString() {
        if (exception != null) {
            return "RZ:ERROR " + exception.getMessage();
        } else if (statusCode == 200) {
            return "RZ:200";
        } else {
            return "RZ:" + statusCode + " " + response;
        }
    }
}