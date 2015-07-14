package com.study.api.service;

import com.study.api.exception.BalanceNotEnoughException;
import com.study.common.StudyLogger;
import com.study.common.util.MessageUtil;
import com.study.dao.AccountBillMapper;
import com.study.dao.AccountMapper;
import com.study.model.Account;
import com.study.model.AccountBill;
import com.study.model.AccountBillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Star on 2015/7/13.
 */
@Service
public class AccountSafety {
    private static Object lockObj = new Object();

    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountBillMapper accountBillMapper;

    public AccountBill updateForDeposit(@RequestBody Integer userId, AccountBillType billType, Long amount){
        synchronized (lockObj){
            Account account = accountMapper.selectByUserId(userId);
            //初次新建账户
            if(null == account){
                account = new Account();
                account.setUserId(userId);
                account.setBalance(0L);
                account.setCreateTime(new Date());
                account.setCreateUser(0);
                accountMapper.insert(account);
                StudyLogger.recBusinessLog("create new account[" + account.getId() + "] for user[" + userId + "]");
            }
            Map map = new HashMap();
            map.put("accountId", account.getId());
            map.put("billTypeId", billType.getId());
            AccountBill bill = accountBillMapper.selectByAccountIdAndBillType(map);
            if(null == bill){
                bill = new AccountBill();
                bill.setAccountId(account.getId());
                bill.setBillTypeId(billType.getId());
                bill.setName(billType.getName());
                bill.setBalance(0L);
                bill.setCreateUser(0);
                bill.setCreateTime(new Date());
                accountBillMapper.insert(bill);
                StudyLogger.recBusinessLog("create new accountBill[" + bill.getId() + "] for user[" + userId + "]");
            }
            StudyLogger.recBusinessLog("before[" + userId + "] ---> accountBalance[" + account.getBalance() + "] | accountBillBalance[" + bill.getBalance() + "] | amount[" + amount + "]");
            bill.setBalance(bill.getBalance() + amount);
            accountBillMapper.updateByPrimaryKey(bill);
            account.setBalance(account.getBalance() + amount);
            accountMapper.updateByPrimaryKey(account);
            StudyLogger.recBusinessLog("after[" + userId + "] ---> accountBalance[" + account.getBalance() + "] | accountBillBalance[" + bill.getBalance() + "] | amount[" + amount + "]");
            return bill;
        }
    }

    public void updateForWithdraw(Account account, AccountBill bill, Long amount) throws Exception{
        synchronized (lockObj){
            if((bill.getBalance()+amount) < 0){
                throw new BalanceNotEnoughException(messageUtil.getMessage("msg.balance.notEnough"));
            }
            StudyLogger.recBusinessLog("before[" + account.getUserId() + "] ---> accountBalance[" + account.getBalance() + "] | accountBillBalance[" + bill.getBalance() + "] | amount[" + amount + "]");
            bill.setBalance(bill.getBalance() + amount);
            accountBillMapper.updateByPrimaryKey(bill);
            account.setBalance(account.getBalance() + amount);
            accountMapper.updateByPrimaryKey(account);
            StudyLogger.recBusinessLog("after[" + account.getUserId() + "] ---> accountBalance[" + account.getBalance() + "] | accountBillBalance[" + bill.getBalance() + "] | amount[" + amount + "]");
        }
    }
}
