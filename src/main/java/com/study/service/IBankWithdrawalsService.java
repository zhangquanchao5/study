package com.study.service;

import com.study.common.apibean.request.PreWithdrawReq;
import com.study.common.apibean.request.WithdrawConfirmReq;

/**
 * Created by Star on 2016/4/20.
 */
public interface IBankWithdrawalsService {

    /**
     * Prewithdraw.
     *
     * @param req the req
     * @throws Exception the exception
     */
    void prewithdraw(PreWithdrawReq req) throws Exception;

    /**
     * Confirm withdraw.
     *
     * @param req the req
     * @throws Exception the exception
     */
    void confirmWithdraw(WithdrawConfirmReq req) throws Exception;
}
