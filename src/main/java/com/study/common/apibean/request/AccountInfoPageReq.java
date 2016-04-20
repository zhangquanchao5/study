package com.study.common.apibean.request;

import com.study.common.page.BasePageRequest;

/**
 * Created by huichao on 2016/4/19.
 */
public class AccountInfoPageReq extends BasePageRequest {

    private Integer id;

    private Integer start;

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
