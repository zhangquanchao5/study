package com.study.service.impl;

import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.apibean.response.BankWithDrawResp;
import com.study.dao.BankWithdrawalsMapper;
import com.study.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huichao on 2016/4/20.
 */
@Service
public class BankServiceImpl implements IBankService {

    @Autowired
    private BankWithdrawalsMapper bankWithdrawalsMapper;

    public List<BankWithDrawResp> findPageWithDraw(BankWithdrawReq bankWithdrawReq){

        return null;
    }
}
