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
    void saveForPrewithdraw(PreWithdrawReq req) throws Exception;

    /**
     * Confirm withdraw.
     *
     * @param req the req
     * @throws Exception the exception
     */
    void saveForConfirmWithdraw(WithdrawConfirmReq req) throws Exception;
}
