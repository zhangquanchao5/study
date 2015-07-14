package com.study.dao;


import com.study.model.AccountBill;

import java.util.List;
import java.util.Map;

public interface AccountBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountBill record);

    int insertSelective(AccountBill record);

    AccountBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountBill record);

    int updateByPrimaryKey(AccountBill record);

    AccountBill selectByAccountIdAndBillType(Map map);

    List<AccountBill> selectByAccountId(Integer accountId);
}