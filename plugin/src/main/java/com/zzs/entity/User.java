package com.zzs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author mountain
 * @since 2021/5/29 23:50
 */

@Data
@TableName(value = "t_user")
@Accessors(chain = true)
public class User implements Serializable {
    @TableId(value = "uuid")
    private String uuid;

    private String userName;

    private String ipAddress;

    private String password;

    private Boolean isBan;

    private Boolean isLogin;
}
