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

    int registerAccount(@Param("userName") String userName, @Param("ipAddress") String ipAddress, @Param("uuid") String uuid);

    User findByNameAndUuid(@Param("userName") String userName, @Param("uuid") String uuid);
}
