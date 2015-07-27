package com.study.common.apibean.response;

/**
 * Created by Star on 2015/7/14.
 */
public class AccountBook {
    private Integer id;
    private Integer type;
    private Long amount;
    private String desc;
    private Long ct;

    public AccountBook() {
    }

    public AccountBook(Integer id, Integer type, Long amount, String desc, Long ct) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.desc = desc;
        this.ct = ct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getCt() {
        return ct;
    }

    public void setCt(Long ct) {
        this.ct = ct;
    }
}
