package com.zzs.util;


import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mountain
 * @since 2021/5/29 23:56
 */
public class SqlSessionUtil {

    public static SqlSession getSqlSession() {
        String resource = "mybatis/SqlMapperConfig.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
        return sqlSession;
    }

    public static void closeSqlSession(SqlSession sqlSession) {
        try {
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}