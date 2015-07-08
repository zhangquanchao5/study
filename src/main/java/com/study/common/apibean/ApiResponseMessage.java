package com.study.common.apibean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zqc
 */
public class ApiResponseMessage {
    private String msg;
    private String code;
    private Object data;
    private List datas;

    /**
     * Instantiates a new Ajax response message.
     */
    public ApiResponseMessage(){}


    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Gets datas.
     *
     * @return the datas
     */
    public List getDatas() {
        return datas;
    }

    /**
     * Sets datas.
     *
     * @param datas the datas
     */
    public void setDatas(List datas) {
        this.datas = datas;
    }
}
