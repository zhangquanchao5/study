package com.study.dao;

import com.study.model.BankWithdrawals;

public interface BankWithdrawalsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BankWithdrawals record);

    int insertSelective(BankWithdrawals record);

    BankWithdrawals selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankWithdrawals record);

    int updateByPrimaryKey(BankWithdrawals record);
}