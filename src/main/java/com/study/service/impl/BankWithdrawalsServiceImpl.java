package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.common.apibean.request.WithdrawConfirmReq;
import com.study.common.util.MessageUtil;
import com.study.dao.BankWithdrawalsMapper;
import com.study.exception.BankWithDrawalsNotExitsException;
import com.study.model.BankWithdrawals;
import com.study.service.IBankWithdrawalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Star on 2016/4/20.
 */
@Service
public class BankWithdrawalsServiceImpl implements IBankWithdrawalsService {
    @Autowired
    private MessageUtil messageUtil;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BankWithdrawalsMapper bankWithdrawalsMapper;

    @Override
    public void confirmWithdraw(WithdrawConfirmReq req) throws Exception{
        BankWithdrawals bankWithdrawals = bankWithdrawalsMapper.selectByPrimaryKey(req.getWithdrawNo());
        if (null != bankWithdrawals) {
            bankWithdrawals.setStatus(req.getStatus().byteValue());
            if (req.getStatus() == EntityCode.WITHDRAW_CONFIRM_SUCC) {
                bankWithdrawals.setTransferTime(new Date());
            }
            bankWithdrawalsMapper.updateByPrimaryKey(bankWithdrawals);
        } else {
            throw new BankWithDrawalsNotExitsException(messageUtil.getMessage("msg.bankWithdraw.notExits"));
        }
    }
}
