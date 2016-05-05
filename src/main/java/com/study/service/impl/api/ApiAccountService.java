package com.study.service.impl.api;

import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.common.StudyLogger;
import com.study.common.apibean.AccountDetailBean;
import com.study.common.apibean.request.AccountInfoPageReq;
import com.study.common.apibean.request.RechargeReq;
import com.study.common.apibean.response.AccountBook;
import com.study.common.apibean.response.AccountDetailResp;
import com.study.common.apibean.response.AccountInfoResp;
import com.study.common.apibean.request.DepositAndWithdrawReq;
import com.study.common.apibean.response.CommonResponse;
import com.study.common.bean.AccountDetailVo;
import com.study.common.sms.SendSm;
import com.study.common.sms.SmsResponse;
import com.study.common.util.DESUtils;
import com.study.common.util.EncryptUtil;
import com.study.exception.*;
import com.study.common.StringUtil;
import com.study.common.util.MessageUtil;
import com.study.dao.*;
import com.study.model.*;
import com.study.service.IRedisService;
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
    private UserInfoFromMapper userInfoFromMapper;

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

    @Autowired
    private IRedisService iRedisService;

    public AccountDetailResp getAccountHistory( AccountInfoPageReq req) throws Exception {
        AccountDetailResp accountInfoPageReq=new AccountDetailResp();
        req.setStart(((req.getPage() == null ? 0 : req.getPage() - 1)) * (req.getSize() == null ? 15 : req.getSize()));
        req.setSize(req.getSize() == null ? 15 : req.getSize());
        if(null == req.getId()){
            throw new ParameterNotEnoughException(messageUtil.getMessage("msg.parameter.notEnough"));
        }

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(req.getId());
        if(null == userInfo){
            throw new UserNotExitsException(messageUtil.getMessage("msg.user.notExits"));
        }

        Account account = accountMapper.selectByUserId(userInfo.getId());
        if(null == account){
            throw new AccountNotExitsException(messageUtil.getMessage("msg.user.accountNotExits"));
        }
        accountInfoPageReq.setAmount(account.getBalance());
        accountInfoPageReq.setCashAmount(0L);
        accountInfoPageReq.setGiftAmount(0l);
        accountInfoPageReq.setRedAmount(0l);
        List<AccountBill> bills = accountBillMapper.selectByAccountId(account.getId());
        if(null != bills && bills.size() > 0){
            for(AccountBill bill : bills){
                if(bill.getBillTypeId().intValue()==EntityCode.BILLTYPE_CODE_CASH_ID){
                    accountInfoPageReq.setCashAmount(bill.getBalance());
                }else if(bill.getBillTypeId().intValue()==EntityCode.BILLTYPE_CODE_GIFT_ID){
                    accountInfoPageReq.setGiftAmount(bill.getBalance());
                }else if(bill.getBillTypeId().intValue()==EntityCode.BILLTYPE_CODE_RED_ID){
                    accountInfoPageReq.setRedAmount(bill.getBalance());
                }
            }
        }
        //查询收支明细
        List<AccountDetailVo> accountDetailVos=accountMapper.findHistoryList(req);
        List<AccountDetailBean> accountDetailBeans=new ArrayList<AccountDetailBean>();
        if(accountDetailVos!=null&&accountDetailVos.size()>0){
            AccountDetailBean accountDetailBean;
            for(AccountDetailVo accountDetailVo:accountDetailVos){
                accountDetailBean=new AccountDetailBean();
                accountDetailBean.setAmount(accountDetailVo.getAmount());
                accountDetailBean.setCreateDate(accountDetailVo.getCreateDate());
                accountDetailBean.setStatus(accountDetailVo.getStatus());
                accountDetailBean.setTradeNo(accountDetailVo.getTradeNo());
                if(accountDetailVo.getType().equals("1")){
                    if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_CASH)){
                        if(accountDetailVo.getTradeNo().startsWith("U_")){
                            accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_4);
                        }else{
                            accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_1);
                        }
                    }else if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_GIFT)){
                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_3);
                    }else if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_RED)){
                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_2);
                    }
                }else if(accountDetailVo.getType().equals("2")){
                    if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_CASH)){
                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_5);
                    }else if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_GIFT)){
                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_7);
                    }else if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_RED)){
                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_6);
                    }
                }else{
                    accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_8);
                }
                if(accountDetailBean.getTradeNo().startsWith("U_")){
                    accountDetailBean.setTradeNo(accountDetailBean.getTradeNo().replace("U_",""));
                }

                accountDetailBeans.add(accountDetailBean);

            }
        }

        accountInfoPageReq.setTotal(accountMapper.findHistoryListCount(req));
        accountInfoPageReq.setAccountDetailBeans(accountDetailBeans);
        return  accountInfoPageReq;
    }
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
            throw new RepeatDepositException(messageUtil.getMessage("msg.balance.repeatDeposit"));
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
        depositHistory.setLeftAmount(accountMapper.selectByUserId(userInfo.getId()).getBalance().intValue()-req.getAmount().longValue());
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

        //检查重复扣款
        List<AccountWithdrawalHistory> olds = accountWithdrawalHistoryMapper.findByTradeNo(req.getTradeNO());
        if(null != olds && olds.size() > 0){
            throw new RepeatWithdrawException(messageUtil.getMessage("msg.balance.repeatWithdraw"));
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
        withdrawalHistory.setLeftAmount(account.getBalance().intValue()+req.getAmount().longValue());
        accountWithdrawalHistoryMapper.insert(withdrawalHistory);

        //判断机构编号是否为空，不为空钱转到教育机构
        if(!StringUtil.isEmpty(req.getOrgId())&&req.getOrgId().intValue()!=userId.intValue()){
            //充值
            UserInfo userInfoOrg = userInfoMapper.selectByPrimaryKey(req.getOrgId());
            if(userInfoOrg!=null){
                AccountBillType billTypeOrg = accountBillTypeMapper.selectByCode("cash");
                //充值
                AccountBill billOrg = accountSafety.updateForDeposit(userInfoOrg.getId(), billTypeOrg, req.getAmount().longValue());
                //充值历史
                AccountDepositHistory depositHistory = new AccountDepositHistory();
                depositHistory.setAccountId(billOrg.getAccountId());
                depositHistory.setAccountBillId(billOrg.getId());
                depositHistory.setUserId(userInfoOrg.getId());
                depositHistory.setBillTypeCode(billTypeOrg.getCode());
                depositHistory.setAmount(req.getAmount().longValue());
                depositHistory.setCreateTime(new Date());
                depositHistory.setCreateUser(userId);
                depositHistory.setTradeNo("U_"+req.getTradeNO());
                depositHistory.setLeftAmount(accountMapper.selectByUserId(userInfoOrg.getId()).getBalance().intValue()-req.getAmount().longValue());
                accountDepositHistoryMapper.insert(depositHistory);
            }
        }
    }

    public void updatePayPassword(Integer userId, String newPayPassword) throws Exception{
        UserSecurity userSecurity = userSecurityMapper.selectByUserId(userId);
        if(null == userSecurity || null == newPayPassword || newPayPassword.trim().length() <= 0){
            throw new ParameterNotEnoughException(messageUtil.getMessage("msg.parameter.notEnough"));
        }
        userSecurity.setPayPassword(StringUtil.getMD5Str(newPayPassword));
        userSecurityMapper.updateByPrimaryKey(userSecurity);
    }

    /**
     * 红包充值用户不存在默认新建，发短信
     */
    public CommonResponse saveRedRecharge(RechargeReq rechargeReq,CommonResponse commonResponse) throws Exception{
        //md5校验
        String mobile= DESUtils.decrypt(rechargeReq.getMobile(),DESUtils.secretKey);
        String money =DESUtils.decrypt(rechargeReq.getMoney().toString(),DESUtils.secretKey);
        if(!EncryptUtil.encrypt(mobile+""+money,EncryptUtil.MD5).equals(rechargeReq.getAuthKey())){
            commonResponse.setCode(ErrorCode.RED_RECHARGE_CODE_ERROR);
            return commonResponse;
        }
        //判断用户是否是新用户
        UserInfo userInfo=userInfoMapper.selectByMobile(mobile);
        if(userInfo==null){
            userInfo=userInfoMapper.findByUserName(mobile);
            if(userInfo==null){
                //新用户创建，发送密码给用户
                String code=StringUtil.generateTextCode(0, 6, null);//StringUtil.generateTextCode(0, 6, null);
                userInfo=new UserInfo();
                userInfo.setCreateTime(new Date());
                userInfo.setMobile(mobile);
                userInfo.setSource(EntityCode.USER_SOURCE_APP);
                userInfo.setPassword(StringUtil.getMD5Str("ZHANGHUICHAO$%SD"));
                userInfo.setStatus(EntityCode.USER_NEED_ACTIVE);
                userInfoMapper.insert(userInfo);

                UserInfoFrom userInfoFrom=new UserInfoFrom();
                userInfoFrom.setUserId(userInfo.getId());
                userInfoFrom.setFrom(EntityCode.USER_FROM_MOBILE);

                userInfoFromMapper.insert(userInfoFrom);

                UserSecurity userSecurity = new UserSecurity();
                userSecurity.setUserId(userInfo.getId());
                userSecurity.setCreateTime(new Date());
                userSecurityMapper.insert(userSecurity);
                //发送短信
                SmsResponse smsResponse= SendSm.sendSms(mobile, messageUtil.getMessage("MSG.SMSSEND.CONTENT").replace("#CODE", code));
                System.out.println(smsResponse.getCode() + ":" + smsResponse.getMsg() + ":" + smsResponse.getSmsid());
                if(!smsResponse.getCode().equals(SendSm.SUCCE_CODE)){
                    commonResponse.setCode(ErrorCode.RED_RECHARGE_SEND_ERROR);
                }else{
                    iRedisService.set(PrefixCode.API_MOBILE_LOGIN_ON  + mobile, code, 604800);
                }
            }
        }

        DepositAndWithdrawReq depositAndWithdrawReq=new DepositAndWithdrawReq();
        depositAndWithdrawReq.setAmount(Integer.parseInt(money));
        depositAndWithdrawReq.setTradeNO(UUID.randomUUID().toString());
        depositAndWithdrawReq.setAccountBIllType(rechargeReq.getAccountBIllType());
        saveForDeposit(userInfo.getId(), depositAndWithdrawReq);

        return commonResponse;
    }
}
