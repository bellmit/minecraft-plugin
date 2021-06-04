package com.zzs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author mountain
 * @since 2021/5/29 23:50
 */

@Data
@TableName(value = "t_user")
public class User {
    private Long id;

    private String userName;

    private String ipAddress;

    private String uuid;

    private String password;

    private Boolean isBan;

    private Boolean isLogin;
}
