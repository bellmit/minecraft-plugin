package com.zzs.util;


import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mountain
 * @since 2021/5/29 23:56
 */
public class SqlSessionUtil {

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSqlSessionFactory() {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatisPlusConfig.xml");
            MybatisSqlSessionFactoryBuilder mybatisSqlSessionFactoryBuilder = new MybatisSqlSessionFactoryBuilder();
            if (sqlSessionFactory == null) {
                sqlSessionFactory = mybatisSqlSessionFactoryBuilder.build(inputStream);
            }
            return sqlSessionFactory;
        } catch (IOException e) {
            Bukkit.getLogger().info("获取数据库配置文件失败");
            e.printStackTrace();
            return null;
        }
    }

}