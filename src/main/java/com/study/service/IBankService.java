package com.study.service;

import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.request.BankBindReq;
import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.apibean.response.BankWithDrawResp;
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
     * @param userId the user id
     * @param req    the req
     * @throws Exception the exception
     */
    void bindBank(Integer userId, BankBindReq req) throws Exception;

    /**
     * Unbind bank.
     *
     * @param id the id
     * @throws Exception the exception
     */
    void unbindBank(Integer id) throws Exception;

    /**
     * Find all banks.
     *
     * @param userId the user id
     * @return the list
     * @throws Exception the exception
     */
    List findAllBanks(Integer userId) throws Exception;
}
