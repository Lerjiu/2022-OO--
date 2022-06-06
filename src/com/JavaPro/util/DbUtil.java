package com.JavaPro.util;

import java.sql.*;

/**
 * @describe 数据库工具类
 * @author 周文瑞 20373804
 */
public class DbUtil {
    /**
     * @describe  连接数据库
     * @return con
     * @throws Exception
     */
    public static Connection getConnection()throws Exception{

        String dbUrl = "jdbc:mysql://43.138.40.55:3306/JavaPro"; //数据库连接地址
        String dbUsername = "javauser"; //数据库用户名字
        String dbPassWord = "123456";   //数据库用户密码
        String jdbcName = "com.mysql.jdbc.Driver";  //mysql驱动;
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(dbUrl,dbUsername,dbPassWord);
        return con;
    }

    /**
     * @describe  增删资源的关闭
     * @param con
     */
    public static void closeRecourse(Connection con, PreparedStatement ps){
        try{
            if(ps != null)
                ps.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            if(con != null)
                con.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @describe 查询资源的关闭
     * @param con
     * @param ps
     * @param rs
     */
    public static void closeRecourse(Connection con, PreparedStatement ps, ResultSet rs){
        try {
            if(con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
       public static void main(String[] args){
        try{
             DbUtil.getCon();
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
