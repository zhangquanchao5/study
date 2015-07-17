package com.study.service.impl.api;

import com.study.api.bean.AccountBook;
import com.study.api.bean.AccountInfoResp;
import com.study.api.bean.DepositAndWithdrawReq;
import com.study.api.exception.ParameterNotEnoughException;
import com.study.api.exception.ProcessFailureException;
import com.study.api.exception.UserNotExitsException;
import com.study.common.util.MessageUtil;
import com.study.dao.*;
import com.study.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Star on 2015/7/13.
 */
@Service
public class ApiAccountService {

    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private AccountSafety accountSafety;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountBillMapper accountBillMapper;
    @Autowired
    private AccountBillTypeMapper accountBillTypeMapper;
    @Autowired
    private AccountDepositHistoryMapper accountDepositHistoryMapper;
    @Autowired
    private AccountWithdrawalHistoryMapper accountWithdrawalHistoryMapper;

    public AccountInfoResp getAccountInfo(Integer userId) throws Exception {
        if(null == userId){
            throw new ParameterNotEnoughException(messageUtil.getMessage("msg.parameter.notEnough"));
        }

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(null == userInfo){
            throw new UserNotExitsException(messageUtil.getMessage("msg.user.notExits"));
        }

        Account account = accountMapper.selectByUserId(userInfo.getId());
        if(null == account){
            throw new ProcessFailureException(messageUtil.getMessage("msg.process.fail"));
        }
        List<AccountBook> books = null;
        List<AccountBill> bills = accountBillMapper.selectByAccountId(account.getId());
        if(null != bills && bills.size() > 0){
            books = new ArrayList<AccountBook>();
            for(AccountBill bill : bills){
                books.add(new AccountBook(bill.getId(), bill.getBillTypeId(), bill.getBalance(), bill.getName(), bill.getCreateTime().getTime()));
            }
        }
        return new AccountInfoResp(account.getId(), account.getUserId(), books, account.getCreateTime().getTime());
    }

    public void saveForDeposit(DepositAndWithdrawReq req) throws Exception{
        if(null == req.getUserId() || null == req.getAccountBIllType() || null == req.getAmount() || req.getAmount() <= 0){
            throw new ParameterNotEnoughException(messageUtil.getMessage("msg.parameter.notEnough"));
        }

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(req.getUserId());
        if(null == userInfo){
            throw new UserNotExitsException(messageUtil.getMessage("msg.user.notExits"));
        }

        AccountBillType billType = accountBillTypeMapper.selectByCode(req.getAccountBIllType());
        if(null == billType){
            throw new ProcessFailureException(messageUtil.getMessage("msg.process.fail"));
        }

        AccountBill bill = accountSafety.updateForDeposit(userInfo.getId(), billType, req.getAmount().longValue());
        AccountDepositHistory depositHistory = new AccountDepositHistory();
        depositHistory.setAccountId(bill.getAccountId());
        depositHistory.setAccountBillId(bill.getId());
        depositHistory.setUserid(userInfo.getId());
        depositHistory.setBillTypeCode(billType.getCode());
        depositHistory.setAmount(req.getAmount().longValue());
        depositHistory.setCreateTime(new Date());
        depositHistory.setCreateUser(0);
        accountDepositHistoryMapper.insert(depositHistory);
    }

    public void saveForWithdraw(DepositAndWithdrawReq req) throws Exception {
        if(null == req.getUserId() || null == req.getAccountBIllType() || null == req.getAmount() || req.getAmount() > 0){
            throw new ParameterNotEnoughException(messageUtil.getMessage("msg.parameter.notEnough"));
        }

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(req.getUserId());
        if(null == userInfo){
            throw new UserNotExitsException(messageUtil.getMessage("msg.user.notExits"));
        }

        AccountBillType billType = accountBillTypeMapper.selectByCode(req.getAccountBIllType());
        Account account = accountMapper.selectByUserId(userInfo.getId());
        if(null == billType || null == account){
            throw new ProcessFailureException(messageUtil.getMessage("msg.process.fail"));
        }

        Map map = new HashMap();
        map.put("accountId", account.getId());
        map.put("billTypeId", billType.getId());
        AccountBill bill = accountBillMapper.selectByAccountIdAndBillType(map);
        if(null == bill){
            throw new ProcessFailureException(messageUtil.getMessage("msg.process.fail"));
        }

        accountSafety.updateForWithdraw(account, bill, req.getAmount().longValue());

        //扣款历史
        AccountWithdrawalHistory withdrawalHistory = new AccountWithdrawalHistory();
        withdrawalHistory.setAccountId(bill.getAccountId());
        withdrawalHistory.setAccountBillId(bill.getId());
        withdrawalHistory.setUserid(userInfo.getId());
        withdrawalHistory.setBillTypeCode(billType.getCode());
        withdrawalHistory.setAmount(req.getAmount().longValue());
        withdrawalHistory.setCreateTime(new Date());
        withdrawalHistory.setCreateUser(0);
        accountWithdrawalHistoryMapper.insert(withdrawalHistory);
    }
}
