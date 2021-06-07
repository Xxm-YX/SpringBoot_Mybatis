package com.example.dao;

import com.example.entity.UserDO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 插入用户
     *
     * @param userDO
     */
    void insertUser(UserDO userDO);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    UserDO selectUserById(@Param("id") Long id);
}
