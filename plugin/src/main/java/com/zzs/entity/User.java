package com.zzs.entity;

import lombok.Data;

/**
 * @author mountain
 * @since 2021/5/29 23:50
 */

@Data
public class User {
    private Long id;

    private String userName;

    private String ipAddress;

    private String uuid;
}
