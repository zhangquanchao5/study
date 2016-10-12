package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.common.StringUtil;
import com.study.common.apibean.AccountDetailBean;
import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.request.AccountInfoPageReq;
import com.study.common.apibean.request.BankBindReq;
import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.apibean.response.ApplyPlusResponse;
import com.study.common.apibean.response.BankWithDrawResp;
import com.study.common.bean.AccountDetailVo;
import com.study.common.bean.AccountQueryVo;
import com.study.common.util.MessageUtil;
import com.study.common.util.PropertiesUtil;
import com.study.dao.AccountMapper;
import com.study.dao.BankMapper;
import com.study.dao.BankWithdrawalsMapper;
import com.study.exception.BankDuplicateBindingException;
import com.study.exception.BusinessException;
import com.study.model.Bank;
import com.study.service.IBankService;
import com.study.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BankWithdrawalsMapper bankWithdrawalsMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IRedisService iRedisService;

    public ApiResponseMessage findPageWithDraw(BankWithdrawReq bankWithdrawReq,ApiResponseMessage message){
        bankWithdrawReq.setStart(((bankWithdrawReq.getPage() == null ? 0 : bankWithdrawReq.getPage() - 1)) * (bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize()));
        bankWithdrawReq.setSize(bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize());

        message.setTotal(bankWithdrawalsMapper.findPageWithDrawCount(bankWithdrawReq));
        List<BankWithDrawResp> bankWithDrawResps=bankWithdrawalsMapper.findPageWithDraw(bankWithdrawReq);
        for(BankWithDrawResp bankWithDrawResp:bankWithDrawResps){
            bankWithDrawResp.setBankNo(StringUtil.formatBankNo(bankWithDrawResp.getBankNo()));
        }
        message.setDatas(bankWithDrawResps);
        return message;
    }

    public  ApiResponseMessage findPageAccountQuery(AccountInfoPageReq bankWithdrawReq,ApiResponseMessage message){
        bankWithdrawReq.setStart(((bankWithdrawReq.getPage() == null ? 0 : bankWithdrawReq.getPage() - 1)) * (bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize()));
        bankWithdrawReq.setSize(bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize());

        AccountQueryVo accountQueryVo=accountMapper.findAccountQuery(bankWithdrawReq);
        message.setData(accountQueryVo!=null?accountQueryVo:new AccountQueryVo());
        message.setTotal(accountMapper.findHistoryListCount(bankWithdrawReq));

        //查询收支明细
        List<AccountDetailVo> accountDetailVos=accountMapper.findHistoryList(bankWithdrawReq);
        List<AccountDetailBean> accountDetailBeans=new ArrayList<AccountDetailBean>();
        if(accountDetailVos!=null&&accountDetailVos.size()>0){
            AccountDetailBean accountDetailBean;
            for(AccountDetailVo accountDetailVo:accountDetailVos){
                accountDetailBean=new AccountDetailBean();
                accountDetailBean.setAmount(accountDetailVo.getAmount());
                accountDetailBean.setCreateDate(accountDetailVo.getCreateDate());
                accountDetailBean.setStatus(accountDetailVo.getStatus());
                accountDetailBean.setTradeType(accountDetailVo.getType());
               // accountDetailBean.setTradeNo(accountDetailVo.getTradeNo());
//                if(accountDetailVo.getType().equals("1")){
//                    if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_CASH)){
//                        if(accountDetailVo.getTradeNo().startsWith("U_")){
//                            accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_4);
//                        }else{
//                            accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_1);
//                        }
//                    }else if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_GIFT)){
//                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_3);
//                    }else if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_RED)){
//                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_2);
//                    }
//                }else if(accountDetailVo.getType().equals("2")){
//                    if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_CASH)){
//                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_5);
//                    }else if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_GIFT)){
//                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_7);
//                    }else if(accountDetailVo.getBillType().equals(EntityCode.BILLTYPE_CODE_RED)){
//                        accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_6);
//                    }
//                }else{
//                    accountDetailBean.setTradeType(EntityCode.BILLTYPE_CODE_8);
//                }

                accountDetailBeans.add(accountDetailBean);
            }
        }

        message.setDatas(accountDetailBeans);
        return message;
    }

    @Override
    public Bank saveForBindBank(BankBindReq req) throws BusinessException,BankDuplicateBindingException {
        Bank binded = bankMapper.findByUserIdAndBankNo(req.getUserId(), req.getBankNO());
        if (null != binded) {
            throw new BankDuplicateBindingException(messageUtil.getMessage("msg.bank.duplicateBinding"));
        }
        //个人有手机短信验证
        if(req.getBankPerson()==0&&req.getType()==1){
            if(StringUtil.isEmpty(req.getCode())){
                throw new BusinessException(ErrorCode.USER_CODE_ERROR);
            }else{
                String vcode=iRedisService.get(PrefixCode.API_MOBILE_BANK  + req.getPhone());
                if(StringUtil.isEmpty(vcode)){
                    throw new BusinessException(ErrorCode.USER_CODE_ERROR);
                }
                if(!vcode.equals(req.getCode())){
                    throw new BusinessException(ErrorCode.USER_CODE_ERROR);
                }
            }
        }

        Bank bank = new Bank();
        bank.setUserId(req.getUserId());
        bank.setBankType(req.getType());
        bank.setBankNo(req.getBankNO());
        bank.setBankDeposit(req.getDepositBank());
        bank.setAddress(req.getDepositBankAddress());
        bank.setCreateTime(new Date());
        bank.setName(req.getAccountName());
        bank.setStatus(EntityCode.BANK_VALID);
        bank.setCompanyAddress(req.getCompanyAddress());
        bank.setCompanyCode(req.getCompanyCode());
        bank.setCompanyName(req.getCompanyName());
        bank.setPhone(req.getPhone());
        bank.setBankPerson(req.getBankPerson());
        bankMapper.insert(bank);

         bank.setBankNo(StringUtil.formatBankNo(bank.getBankNo()));
        return bank;
    }

    @Override
    public void saveForUnbindBank(Integer id) throws Exception {
        Bank bank = bankMapper.selectByPrimaryKey(id);
        if (null != bank) {
            bank.setStatus(EntityCode.BANK_INVALID);
            bankMapper.updateByPrimaryKey(bank);
        }
    }

    @Override
    public List findAllBanks(Integer userId) throws Exception {
        List<Bank> banks=bankMapper.findAllByUser(userId);
        for(Bank bank:banks){
            bank.setBankNo(StringUtil.formatBankNo(bank.getBankNo()));
        }
        return banks;
    }

    public ApplyPlusResponse findByUserIdSurplus(Integer useriId){
        ApplyPlusResponse applyPlusResponse=new ApplyPlusResponse();
        applyPlusResponse.setMoney(Integer.parseInt(PropertiesUtil.getString("bankWithdrawal.amount")) / 100);
        applyPlusResponse.setCountApply(Integer.parseInt(PropertiesUtil.getString("bankWithdrawal.nums")));

        Map<String,String> mapBank=new HashMap<String, String>();
        mapBank.put("userId", useriId.toString());
        mapBank.put("amount", PropertiesUtil.getString("bankWithdrawal.amount"));
        int sums=bankWithdrawalsMapper.findMonthSums(mapBank);

        applyPlusResponse.setHasApply(sums);

        return applyPlusResponse;
    }
}
