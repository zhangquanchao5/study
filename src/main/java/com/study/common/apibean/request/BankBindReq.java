package com.study.common.apibean.request;

/**
 * Created by Star on 2016/4/20.
 */
public class BankBindReq {
    private Integer userId;
    private Integer type;
    private String bankNO;
    private String accountName;
    private String depositBank;
    private String depositBankAddress;

    private String companyCode;

    private String companyName;

    private String companyAddress;

    private String phone;

    private Byte bankPerson;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getBankPerson() {
        return bankPerson;
    }

    public void setBankPerson(Byte bankPerson) {
        this.bankPerson = bankPerson;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
     * Gets type.
     *
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Gets bank no.
     *
     * @return the bank no
     */
    public String getBankNO() {
        return bankNO;
    }

    /**
     * Sets bank no.
     *
     * @param bankNO the bank no
     */
    public void setBankNO(String bankNO) {
        this.bankNO = bankNO;
    }

    /**
     * Gets account name.
     *
     * @return the account name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets account name.
     *
     * @param accountName the account name
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Gets deposit bank.
     *
     * @return the deposit bank
     */
    public String getDepositBank() {
        return depositBank;
    }

    /**
     * Sets deposit bank.
     *
     * @param depositBank the deposit bank
     */
    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    /**
     * Gets deposit bank address.
     *
     * @return the deposit bank address
     */
    public String getDepositBankAddress() {
        return depositBankAddress;
    }

    /**
     * Sets deposit bank address.
     *
     * @param depositBankAddress the deposit bank address
     */
    public void setDepositBankAddress(String depositBankAddress) {
        this.depositBankAddress = depositBankAddress;
    }
}
