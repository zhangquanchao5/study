package com.study.common.apibean.request;

/**
 * Created by zqc on 2016/8/17.
 */
public class OrgRemarkReq {
    private Integer userId;

    private String remark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
