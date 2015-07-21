package com.study.dao;

import com.study.model.UserSecurity;

/**
 * The interface User security mapper.
 */
public interface UserSecurityMapper {
    /**
     * Delete by primary key.
     *
     * @param id the id
     * @return the int
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * Insert int.
     *
     * @param record the record
     * @return the int
     */
    int insert(UserSecurity record);

    /**
     * Insert selective.
     *
     * @param record the record
     * @return the int
     */
    int insertSelective(UserSecurity record);

    /**
     * Select by primary key.
     *
     * @param id the id
     * @return the user security
     */
    UserSecurity selectByPrimaryKey(Integer id);

    /**
     * Update by primary key selective.
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKeySelective(UserSecurity record);

    /**
     * Update by primary key.
     *
     * @param record the record
     * @return the int
     */
    int updateByPrimaryKey(UserSecurity record);

    /**
     * Select by user id.
     *
     * @param userId the user id
     * @return the user security
     */
    UserSecurity selectByUserId(Integer userId);
}