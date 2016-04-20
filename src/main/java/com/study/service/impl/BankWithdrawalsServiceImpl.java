package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.common.apibean.request.PreWithdrawReq;
import com.study.common.apibean.request.WithdrawConfirmReq;
import com.study.common.util.MessageUtil;
import com.study.dao.*;
import com.study.exception.*;
import com.study.model.*;
import com.study.service.IBankWithdrawalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Star on 2016/4/20.
 */
@Service
public class BankWithdrawalsServiceImpl implements IBankWithdrawalsService {
    @Autowired
    private MessageUtil messageUtil;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BankMapper bankMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AccountMapper accountMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AccountBillMapper accountBillMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AccountBillTypeMapper accountBillTypeMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BankWithdrawalsMapper bankWithdrawalsMapper;

    @Override
    public void prewithdraw(PreWithdrawReq req) throws Exception {
        //为空默认现金账户
        if (null == req.getCashType() || req.getCashType().trim().length() <= 0) {
            req.setCashType(EntityCode.BILLTYPE_CODE_CASH);
        }
        Bank bank = bankMapper.selectByPrimaryKey(req.getId());
        if (null == bank) {
            throw new BankNotExitsException(messageUtil.getMessage("msg.bank.notExits"));
        }
        Account account = accountMapper.selectByUserId(bank.getUserId());
        if (null == account) {
            throw new AccountNotExitsException(messageUtil.getMessage("msg.user.accountNotExits"));
        }
        if (account.getBalance() < req.getAmount()) {
            throw new AccountBalanceNotEnoughException(messageUtil.getMessage("msg.accout.balanceNotEnough"));
        }
        AccountBillType accountBillType = accountBillTypeMapper.selectByCode(req.getCashType());
        if (null == accountBillType) {
            throw new UnknowAccountBillTypeException(messageUtil.getMessage("msg.billType.unknow"));
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("accountId", account.getId());
        map.put("billTypeId", accountBillType.getId());
        AccountBill accountBill = accountBillMapper.selectByAccountIdAndBillType(map);
        if (null == accountBill || accountBill.getBalance() < req.getAmount()) {
            throw new AccountBillBalanceNotEnoughException(messageUtil.getMessage("msg.accoutBill.balanceNotEnough"));
        }

        accountBill.setBalance(accountBill.getBalance() - req.getAmount());
        accountBillMapper.updateByPrimaryKey(accountBill);

        account.setBalance(account.getBalance() - req.getAmount());
        accountMapper.updateByPrimaryKey(account);

        BankWithdrawals bankWithdrawals = new BankWithdrawals();
        bankWithdrawals.setBankId(bank.getId().toString());
        bankWithdrawals.setUserId(account.getUserId());
        bankWithdrawals.setBankType(bank.getBankType());
        bankWithdrawals.setAmount(req.getAmount().longValue());
        bankWithdrawals.setStatus(Integer.valueOf(EntityCode.WITHDRAW_STATUS_APPLY).byteValue());
        bankWithdrawals.setCreateTime(new Date());
        bankWithdrawals.setLeftAmount(account.getBalance());
        bankWithdrawalsMapper.insert(bankWithdrawals);
    }

    @Override
    public void confirmWithdraw(WithdrawConfirmReq req) throws Exception{
        BankWithdrawals bankWithdrawals = bankWithdrawalsMapper.selectByPrimaryKey(req.getWithdrawNo());
        if (null != bankWithdrawals) {
            bankWithdrawals.setStatus(req.getStatus().byteValue());
            if (req.getStatus() == EntityCode.WITHDRAW_STATUS_SUCC) {
                bankWithdrawals.setTransferTime(new Date());
            }
            bankWithdrawalsMapper.updateByPrimaryKey(bankWithdrawals);
        } else {
            throw new BankWithDrawalsNotExitsException(messageUtil.getMessage("msg.bankWithdraw.notExits"));
        }
    }
}
