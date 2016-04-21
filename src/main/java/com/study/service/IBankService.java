package com.study.service;

import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.request.AccountInfoPageReq;
import com.study.common.apibean.request.BankBindReq;
import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.apibean.response.BankWithDrawResp;
import com.study.model.Bank;
import com.sun.org.apache.bcel.internal.generic.INEG;

import java.util.List;

/**
 * Created by huichao on 2016/4/20.
 */
public interface IBankService {

    /**
     * Find page with draw.
     *
     * @param bankWithdrawReq the bank withdraw req
     * @param message         the message
     * @return the list
     */
    ApiResponseMessage findPageWithDraw(BankWithdrawReq bankWithdrawReq,ApiResponseMessage message);

    /**
     * Bind bank.
     *
     * @param req the req
     * @return the bank
     * @throws Exception the exception
     */
    Bank saveForBindBank(BankBindReq req) throws Exception;

    /**
     * Find page account query.
     *
     * @param bankWithdrawReq the bank withdraw req
     * @param message         the message
     * @return the api response message
     */
    ApiResponseMessage findPageAccountQuery(AccountInfoPageReq bankWithdrawReq,ApiResponseMessage message);

    /**
     * Unbind bank.
     *
     * @param id the id
     * @throws Exception the exception
     */
    void saveForUnbindBank(Integer id) throws Exception;

    /**
     * Find all banks.
     *
     * @param userId the user id
     * @return the list
     * @throws Exception the exception
     */
    List findAllBanks(Integer userId) throws Exception;
}
