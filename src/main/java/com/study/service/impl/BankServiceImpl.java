package com.study.service.impl;

import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.request.BankBindReq;
import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.apibean.response.BankWithDrawResp;
import com.study.common.util.MessageUtil;
import com.study.dao.BankMapper;
import com.study.dao.BankWithdrawalsMapper;
import com.study.exception.BankDuplicateBindingException;
import com.study.model.Bank;
import com.study.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huichao on 2016/4/20.
 */
@Service
public class BankServiceImpl implements IBankService {

    @Autowired
    private MessageUtil messageUtil;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BankMapper bankMapper;

    @Autowired
    private BankWithdrawalsMapper bankWithdrawalsMapper;

    public ApiResponseMessage findPageWithDraw(BankWithdrawReq bankWithdrawReq,ApiResponseMessage message){
        bankWithdrawReq.setStart(((bankWithdrawReq.getPage() == null ? 0 : bankWithdrawReq.getPage() - 1)) * (bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize()));
        bankWithdrawReq.setSize(bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize());

        message.setTotal(bankWithdrawalsMapper.findPageWithDrawCount(bankWithdrawReq));
        message.setDatas(bankWithdrawalsMapper.findPageWithDraw(bankWithdrawReq));
        return message;
    }

    @Override
    public void bindBank(Integer userId, BankBindReq req) throws Exception {
        Bank binded = bankMapper.findByUserIdAndBankNo(userId, req.getBankNO());
        if (null != binded) {
            throw new BankDuplicateBindingException(messageUtil.getMessage("msg.bank.duplicateBinding"));
        }
        Bank bank = new Bank();
        bank.setUserId(userId);
        bank.setBankType(req.getType());
        bank.setBankNo(req.getBankNO());
        bank.setBankDeposit(req.getDepositBank());
        bank.setAddress(req.getDepositBankAddress());
        bank.setCreateTime(new Date());
        bank.setName(req.getAccountName());
        bankMapper.insert(bank);
    }
}
