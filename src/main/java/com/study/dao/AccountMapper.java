package com.study.dao;

import com.study.common.apibean.request.AccountInfoPageReq;
import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.bean.AccountDetailVo;
import com.study.common.bean.AccountQueryVo;
import com.study.model.Account;

import java.util.List;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Account selectByUserId(Integer userId);

    List<AccountDetailVo> findHistoryList(AccountInfoPageReq req);

    int findHistoryListCount(AccountInfoPageReq req);

    AccountQueryVo findAccountQuery(AccountInfoPageReq req);
}