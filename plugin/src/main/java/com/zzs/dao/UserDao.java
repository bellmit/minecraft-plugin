package com.zzs.dao;

import com.zzs.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author mountain
 * @since 2021/5/29 23:53
 */

@Mapper
public interface UserDao {

    int registerAccount(@Param("userName") String userName, @Param("password") String password, @Param("ipAddress") String ipAddress, @Param("uuid") String uuid);

    User findByNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    String findByUserName(@Param("userName") String userName);
}
