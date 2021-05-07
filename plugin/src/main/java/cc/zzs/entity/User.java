package cc.zzs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author mm013
 * @Date 2021/5/7 11:25
 */
@Data
@TableName("user")
public class User {
    private Long id;

    private String userName;

    private String password;
}
