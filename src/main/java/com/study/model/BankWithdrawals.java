package com.study.model;

import java.util.Date;

/**
 * The type Bank withdrawals.
 */
public class BankWithdrawals {
    private Integer id;

    private Integer userId;

    private String bankId;

    private Integer bankType;

    private Long amount;

    private Byte status;

    private Date createTime;

    private Date transferTime;

    private Long leftAmount;

    private Integer billType;

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
     * Gets bank id.
     *
     * @return the bank id
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * Sets bank id.
     *
     * @param bankId the bank id
     */
    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    /**
     * Gets bank type.
     *
     * @return the bank type
     */
    public Integer getBankType() {
        return bankType;
    }

    /**
     * Sets bank type.
     *
     * @param bankType the bank type
     */
    public void setBankType(Integer bankType) {
        this.bankType = bankType;
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
     * Gets status.
     *
     * @return the status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Byte status) {
        this.status = status;
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
     * Gets transfer time.
     *
     * @return the transfer time
     */
    public Date getTransferTime() {
        return transferTime;
    }

    /**
     * Sets transfer time.
     *
     * @param transferTime the transfer time
     */
    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    /**
     * Gets left amount.
     *
     * @return the left amount
     */
    public Long getLeftAmount() {
        return leftAmount;
    }

    /**
     * Sets left amount.
     *
     * @param leftAmount the left amount
     */
    public void setLeftAmount(Long leftAmount) {
        this.leftAmount = leftAmount;
    }

    /**
     * Gets bill type.
     *
     * @return the bill type
     */
    public Integer getBillType() {
        return billType;
    }

    /**
     * Sets bill type.
     *
     * @param billType the bill type
     */
    public void setBillType(Integer billType) {
        this.billType = billType;
    }
}