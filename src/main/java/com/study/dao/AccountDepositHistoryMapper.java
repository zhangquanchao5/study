package com.study.dao;

import com.study.model.AccountDepositHistory;

public interface AccountDepositHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountDepositHistory record);

    int insertSelective(AccountDepositHistory record);

    AccountDepositHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountDepositHistory record);

    int updateByPrimaryKey(AccountDepositHistory record);
}