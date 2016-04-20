package com.study.dao;

import com.study.model.Bank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bank record);

    int insertSelective(Bank record);

    Bank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bank record);

    int updateByPrimaryKey(Bank record);

    Bank findByUserIdAndBankNo(@Param("userId") Integer userId, @Param("bankNo") String bankNo);

    List<Bank> findAllByUser(Integer userId);
}