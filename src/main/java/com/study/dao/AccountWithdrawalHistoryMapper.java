package com.study.dao;

import com.study.model.AccountWithdrawalHistory;

public interface AccountWithdrawalHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountWithdrawalHistory record);

    int insertSelective(AccountWithdrawalHistory record);

    AccountWithdrawalHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountWithdrawalHistory record);

    int updateByPrimaryKey(AccountWithdrawalHistory record);
}