package com.study.service.impl;

import com.study.common.apibean.ApiResponseMessage;
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

    public ApiResponseMessage findPageWithDraw(BankWithdrawReq bankWithdrawReq,ApiResponseMessage message){
        bankWithdrawReq.setStart(((bankWithdrawReq.getPage() == null ? 0 : bankWithdrawReq.getPage() - 1)) * (bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize()));
        bankWithdrawReq.setSize(bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize());

        message.setTotal(bankWithdrawalsMapper.findPageWithDrawCount(bankWithdrawReq));
        message.setDatas(bankWithdrawalsMapper.findPageWithDraw(bankWithdrawReq));
        return message;
    }
}
