package com.study.common.apibean.request;

import com.study.common.page.BasePageRequest;

/**
 * Created by huichao on 2016/4/19.
 */
public class AccountInfoPageReq extends BasePageRequest {

    private Integer id;

    private Integer start;

    private String beginTime;

    private String endTime;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
