package com.study.service;

import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.apibean.response.BankWithDrawResp;

import java.util.List;

/**
 * Created by huichao on 2016/4/20.
 */
public interface IBankService {

    /**
     * Find page with draw.
     *
     * @param bankWithdrawReq the bank withdraw req
     * @param message the message
     * @param message         the message
     * @return the list
     */
    ApiResponseMessage findPageWithDraw(BankWithdrawReq bankWithdrawReq,ApiResponseMessage message);

    /**
     * Bind bank.
     *
     * @param userId the user id
     * @param req    the req
     */
    void bindBank(Integer userId, BankBindReq req) throws Exception;

    /**
     * Find page account query.
     *
     * @param bankWithdrawReq the bank withdraw req
     * @param message the message
     * @return the api response message
     */
    ApiResponseMessage findPageAccountQuery(AccountInfoPageReq bankWithdrawReq,ApiResponseMessage message);
}
