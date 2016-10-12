package com.study.common.apibean.response;

/**
 * Created by zqc on 2016/8/10.
 */
public class UserStatisticsResponse {
    private Integer userAll;
    private Integer currentMonth;
    private String addRate;

    public String getAddRate() {
        return addRate;
    }

    public void setAddRate(String addRate) {
        this.addRate = addRate;
    }

    public Integer getUserAll() {
        return userAll;
    }

    public void setUserAll(Integer userAll) {
        this.userAll = userAll;
    }

    public Integer getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(Integer currentMonth) {
        this.currentMonth = currentMonth;
    }
}
