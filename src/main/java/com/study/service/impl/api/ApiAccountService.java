package com.study.service.impl.api;

import com.study.code.EntityCode;
import com.study.common.StudyLogger;
import com.study.common.apibean.response.AccountBook;
import com.study.common.apibean.response.AccountInfoResp;
import com.study.common.apibean.request.DepositAndWithdrawReq;
import com.study.exception.ParameterNotEnoughException;
import com.study.exception.ProcessFailureException;
import com.study.exception.RechargeException;
import com.study.exception.UserNotExitsException;
import com.study.common.StringUtil;
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
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserInfoMapper userInfoMapper;
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
    private AccountDepositHistoryMapper accountDepositHistoryMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AccountWithdrawalHistoryMapper accountWithdrawalHistoryMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserSecurityMapper userSecurityMapper;

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

    public void saveForDeposit(Integer userId, DepositAndWithdrawReq req) throws Exception{
        if(null == userId || null == req.getAccountBIllType()  || null == req.getTradeNO()|| null == req.getAmount() || req.getAmount() <= 0){
            throw new ParameterNotEnoughException(messageUtil.getMessage("msg.parameter.notEnough"));
        }

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(null == userInfo){
            throw new UserNotExitsException(messageUtil.getMessage("msg.user.notExits"));
        }

        AccountBillType billType = accountBillTypeMapper.selectByCode(req.getAccountBIllType());
        if(null == billType){
            throw new ProcessFailureException(messageUtil.getMessage("msg.process.fail"));
        }

        //检查重复充值
        List<AccountDepositHistory> olds = accountDepositHistoryMapper.findByTradeNo(req.getTradeNO());
        if(null != olds && olds.size() > 0){
            throw new RechargeException(messageUtil.getMessage("msg.balance.recharge"));
        }

        //充值
        AccountBill bill = accountSafety.updateForDeposit(userInfo.getId(), billType, req.getAmount().longValue());
        //充值历史
        AccountDepositHistory depositHistory = new AccountDepositHistory();
        depositHistory.setAccountId(bill.getAccountId());
        depositHistory.setAccountBillId(bill.getId());
        depositHistory.setUserId(userInfo.getId());
        depositHistory.setBillTypeCode(billType.getCode());
        depositHistory.setAmount(req.getAmount().longValue());
        depositHistory.setCreateTime(new Date());
        depositHistory.setCreateUser(0);
        depositHistory.setTradeNo(req.getTradeNO());
        accountDepositHistoryMapper.insert(depositHistory);
    }

    public void saveForWithdraw(Integer userId, DepositAndWithdrawReq req) throws Exception {
        if(null == userId || null == req.getAmount()){
            throw new ParameterNotEnoughException(messageUtil.getMessage("msg.parameter.notEnough"));
        }

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(null == userInfo){
            throw new UserNotExitsException(messageUtil.getMessage("msg.user.notExits"));
        }

        if (null == req.getAccountbook()) {
            req.setAccountbook(EntityCode.BILLTYPE_CODE_CASH);
            StudyLogger.recBusinessLog("bill type code use default:"+EntityCode.BILLTYPE_CODE_CASH);
        }

        AccountBillType billType = accountBillTypeMapper.selectByCode(req.getAccountbook());
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

        //检查重复充值
        List<AccountWithdrawalHistory> olds = accountWithdrawalHistoryMapper.findByTradeNo(req.getTradeNO());
        if(null != olds && olds.size() > 0){
            throw new RechargeException(messageUtil.getMessage("msg.balance.recharge"));
        }

        //扣款
        accountSafety.updateForWithdraw(account, bill, req.getAmount().longValue());

        //扣款历史
        AccountWithdrawalHistory withdrawalHistory = new AccountWithdrawalHistory();
        withdrawalHistory.setAccountId(bill.getAccountId());
        withdrawalHistory.setAccountBillId(bill.getId());
        withdrawalHistory.setUserId(userInfo.getId());
        withdrawalHistory.setBillTypeCode(billType.getCode());
        withdrawalHistory.setAmount(req.getAmount().longValue());
        withdrawalHistory.setCreateTime(new Date());
        withdrawalHistory.setCreateUser(0);
        withdrawalHistory.setTradeNo(req.getTradeNO());
        accountWithdrawalHistoryMapper.insert(withdrawalHistory);
    }

    public void updatePayPassword(Integer userId, String newPayPassword) throws Exception{
        UserSecurity userSecurity = userSecurityMapper.selectByUserId(userId);
        if(null == userSecurity || null == newPayPassword || newPayPassword.trim().length() <= 0){
            throw new ParameterNotEnoughException(messageUtil.getMessage("msg.parameter.notEnough"));
        }
        userSecurity.setPayPassword(StringUtil.getMD5Str(newPayPassword));
        userSecurityMapper.updateByPrimaryKey(userSecurity);
    }
}
