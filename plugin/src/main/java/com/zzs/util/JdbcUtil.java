package com.zzs.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author mm013
 * @Date 2021/5/8 10:07
 */
public class JdbcUtil {
    //私有变量
    private static String driver;
    private static String url;
    private static String userName;
    private static String password;

    static {
        try {
            //1.新建属性集对象
            Properties properties = new Properties();
            //2通过反射，新建字符输入流，读取db.properties文件
            InputStream input = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //3.将输入流中读取到的属性，加载到properties属性集对象中
            properties.load(input);
            //4.根据键，获取properties中对应的值
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            userName = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //返回数据库连接
    public static Connection getConnection() {
        try {
            //注册数据库的驱动
            Class.forName(driver);
            //获取数据库连接（里面内容依次是：主机名和端口、用户名、密码）
            Connection connection = DriverManager.getConnection(url, userName, password);
            //返回数据库连接
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭结果集
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭预处理对象
    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭数据库连接
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //一次性关闭上面三个
    public static void closeResource(PreparedStatement preparedStatement, Connection connection) {
        closePreparedStatement(preparedStatement);
        closeConnection(connection);
    }


}
