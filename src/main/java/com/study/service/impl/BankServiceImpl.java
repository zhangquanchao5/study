package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.common.apibean.AccountDetailBean;
import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.request.AccountInfoPageReq;
import com.study.common.apibean.request.BankBindReq;
import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.apibean.response.BankWithDrawResp;
import com.study.common.bean.AccountDetailVo;
import com.study.common.util.MessageUtil;
import com.study.dao.AccountMapper;
import com.study.dao.BankMapper;
import com.study.dao.BankWithdrawalsMapper;
import com.study.exception.BankDuplicateBindingException;
import com.study.model.Bank;
import com.study.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BankWithdrawalsMapper bankWithdrawalsMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AccountMapper accountMapper;

    public ApiResponseMessage findPageWithDraw(BankWithdrawReq bankWithdrawReq,ApiResponseMessage message){
        bankWithdrawReq.setStart(((bankWithdrawReq.getPage() == null ? 0 : bankWithdrawReq.getPage() - 1)) * (bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize()));
        bankWithdrawReq.setSize(bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize());

        message.setTotal(bankWithdrawalsMapper.findPageWithDrawCount(bankWithdrawReq));
        message.setDatas(bankWithdrawalsMapper.findPageWithDraw(bankWithdrawReq));
        return message;
    }

    public  ApiResponseMessage findPageAccountQuery(AccountInfoPageReq bankWithdrawReq,ApiResponseMessage message){
        bankWithdrawReq.setStart(((bankWithdrawReq.getPage() == null ? 0 : bankWithdrawReq.getPage() - 1)) * (bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize()));
        bankWithdrawReq.setSize(bankWithdrawReq.getSize() == null ? 15 : bankWithdrawReq.getSize());

        message.setData(accountMapper.findAccountQuery(bankWithdrawReq));
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
    public Bank saveForBindBank(BankBindReq req) throws Exception {
        Bank binded = bankMapper.findByUserIdAndBankNo(req.getUserId(), req.getBankNO());
        if (null != binded) {
            throw new BankDuplicateBindingException(messageUtil.getMessage("msg.bank.duplicateBinding"));
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
        bankMapper.insert(bank);
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
        return bankMapper.findAllByUser(userId);
    }
}
