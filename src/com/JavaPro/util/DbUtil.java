package com.JavaPro.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 * @author 周文瑞 20373804
 */
public class DbUtil {
    private String dbUrl = "jdbc:mysql://43.138.40.55:3306/JavaPro"; //数据库连接地址
    private String dbUsername = "javauser"; //数据库用户名字
    private String dbPassWord = "123456";   //数据库用户密码
    private String jdbcName = "com.mysql.jdbc.Driver";  //mysql驱动;

    /**
     * 连接数据库
     * @return con
     * @throws Exception
     */
    public Connection getCon()throws Exception{
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(dbUrl,dbUsername,dbPassWord);
        return con;
    }

    /**
     * 关闭数据库连接
     * @param con
     * @throws Exception
     */
    public void closeCon(Connection con)throws Exception{
        if(con != null)
        {
            con.close();
        }
    }

/**
 * 这是数据库连接测试
       public static void main(String[] args){
        DbUtil dbUtil = new DbUtil();
        try{
            Connection con = dbUtil.getCon();
            System.out.println("数据库连接成功!");
            dbUtil.closeCon(con);
            System.out.println("数据库断开连接!");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库连接失败!");
        }
    }
 */
}
