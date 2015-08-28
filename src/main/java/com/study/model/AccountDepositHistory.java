package com.study.model;

import java.util.Date;

/**
 * The type Account deposit history.
 */
public class AccountDepositHistory {
    private Integer id;

    private Integer userId;

    private Integer accountId;

    private Integer accountBillId;

    private String billTypeCode;

    private Long amount;

    private Date createTime;

    private Integer createUser;

    private String tradeNo;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets account id.
     *
     * @return the account id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * Sets account id.
     *
     * @param accountId the account id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets account bill id.
     *
     * @return the account bill id
     */
    public Integer getAccountBillId() {
        return accountBillId;
    }

    /**
     * Sets account bill id.
     *
     * @param accountBillId the account bill id
     */
    public void setAccountBillId(Integer accountBillId) {
        this.accountBillId = accountBillId;
    }

    /**
     * Gets bill type code.
     *
     * @return the bill type code
     */
    public String getBillTypeCode() {
        return billTypeCode;
    }

    /**
     * Sets bill type code.
     *
     * @param billTypeCode the bill type code
     */
    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode == null ? null : billTypeCode.trim();
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * Gets create time.
     *
     * @return the create time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Sets create time.
     *
     * @param createTime the create time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets create user.
     *
     * @return the create user
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * Sets create user.
     *
     * @param createUser the create user
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * Gets trade no.
     *
     * @return the trade no
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * Sets trade no.
     *
     * @param tradeNo the trade no
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}