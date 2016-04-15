package com.study.common.apibean.request;

/**
 * Created by Star on 2015/7/9.
 */
public class DepositAndWithdrawReq {

    private String accountBIllType;
    private Integer amount;
    private String tradeNO;
    private String accountbook;
    private Integer orgId;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    /**
     * Gets account b ill type.
     *
     * @return the account b ill type
     */
    public String getAccountBIllType() {
        return accountBIllType;
    }

    /**
     * Sets account b ill type.
     *
     * @param accountBIllType the account b ill type
     */
    public void setAccountBIllType(String accountBIllType) {
        this.accountBIllType = accountBIllType;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Gets trade nO.
     *
     * @return the trade nO
     */
    public String getTradeNO() {
        return tradeNO;
    }

    /**
     * Sets trade nO.
     *
     * @param tradeNO the trade nO
     */
    public void setTradeNO(String tradeNO) {
        this.tradeNO = tradeNO;
    }

    /**
     * Gets accountbook.
     *
     * @return the accountbook
     */
    public String getAccountbook() {
        return accountbook;
    }

    /**
     * Sets accountbook.
     *
     * @param accountbook the accountbook
     */
    public void setAccountbook(String accountbook) {
        this.accountbook = accountbook;
    }
}
