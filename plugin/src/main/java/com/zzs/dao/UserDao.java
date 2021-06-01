package com.zzs.dao;

import com.zzs.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author mountain
 * @since 2021/5/29 23:53
 */
public interface UserDao extends BaseDao {

    int registerAccount(@Param("userName") String userName, @Param("password") String password, @Param("ipAddress") String ipAddress, @Param("uuid") String uuid);

    User findByUuidAndPassword(@Param("uuid") String uuid, @Param("password") String password);

    String findUserNameByUserName(@Param("userName") String userName);

    void updateIsLoginByUuid(@Param("uuid") String uuid, @Param("isLogin") Boolean isLogin);

    Boolean findIsLoginByUuid(@Param("uuid") String uuid);
}
