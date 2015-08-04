package com.study.common.page;

/**
 * Created by huichao on 2015/8/4.
 */
public class BasePageRequest {

    private Integer page;

    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
