package com.study.common.apibean.response;

import java.util.List;

/**
 * Created by Star on 2015/7/14.
 */
public class AccountInfoResp {
    private Integer id;
    private Integer uid;
    private List<AccountBook> books;
    private Long ct;

    public AccountInfoResp() {
    }

    public AccountInfoResp(Integer id, Integer uid, List<AccountBook> books, Long ct) {
        this.id = id;
        this.uid = uid;
        this.books = books;
        this.ct = ct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public List<AccountBook> getBooks() {
        return books;
    }

    public void setBooks(List<AccountBook> books) {
        this.books = books;
    }

    public Long getCt() {
        return ct;
    }

    public void setCt(Long ct) {
        this.ct = ct;
    }
}
