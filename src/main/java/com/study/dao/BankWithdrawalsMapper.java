package com.study.dao;

import com.study.common.apibean.request.BankWithdrawReq;
import com.study.common.apibean.response.BankWithDrawResp;
import com.study.model.BankWithdrawals;

import java.util.List;

public interface BankWithdrawalsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BankWithdrawals record);

    int insertSelective(BankWithdrawals record);

    BankWithdrawals selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankWithdrawals record);

    int updateByPrimaryKey(BankWithdrawals record);

    List<BankWithDrawResp> findPageWithDraw(BankWithdrawReq bankWithdrawReq);

    int  findPageWithDrawCount(BankWithdrawReq bankWithdrawReq);
}