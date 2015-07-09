package com.study.dao;


import com.study.model.AccountBill;

public interface AccountBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountBill record);

    int insertSelective(AccountBill record);

    AccountBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountBill record);

    int updateByPrimaryKey(AccountBill record);
}