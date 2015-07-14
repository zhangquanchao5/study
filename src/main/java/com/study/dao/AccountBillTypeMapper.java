package com.study.dao;

import com.study.model.AccountBillType;

public interface AccountBillTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountBillType record);

    int insertSelective(AccountBillType record);

    AccountBillType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountBillType record);

    int updateByPrimaryKey(AccountBillType record);

    AccountBillType selectByCode(String code);
}