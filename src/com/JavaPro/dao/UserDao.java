package com.JavaPro.dao;

import com.JavaPro.model.User;

import java.sql.*;
import java.text.MessageFormat;

/**
 * 用户Dao类
 * @author 周文瑞 20373804
 */
public class UserDao {
    /**
     * 用户登录函数
     * @param con 连接数据库的Connection类
     * @param u     用户类
     * @return resultUser 用户类
     * @throws Exception
     */
    public User login(Connection con, User u)throws Exception{
        User resultUser = null;
        String sql = "SELECT * FROM user WHERE uname = ? AND passwd = ?";
        PreparedStatement pstmt =  con.prepareStatement(sql);
        pstmt.setString(1,u.getUserName()); //设置问号1内容
        pstmt.setString(2,u.getPassword()); //设置问号2内容
        ResultSet rs =  pstmt.executeQuery();   //执行sql语句,返回一个结果集
        if(rs.next()) {
            resultUser = new User();
            resultUser.setUid(rs.getInt("uid"));
            resultUser.setUserName(rs.getString("uname"));
            resultUser.setPassword(rs.getString("passwd"));
            System.out.println("登录成功!");
        }
        pstmt.close();
        return resultUser;
    }

    /**
     * 用户注册函数
     * @param con 连接数据库的Connection类
     * @param u 用户类
     * @return
     * @throws Exception
     */
    public int register (Connection con, User u)throws Exception{
        String sql = "INSERT INTO user(uname,passwd) VALUES(?,?)";
        String UID;
        int result;
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,u.getUserName());
        pstmt.setString(2,u.getPassword());
        result = pstmt.executeUpdate();
        pstmt.close();
        //插入新用户↑
        if( result == 0 )   return result;
        //是否插入成功↑
        String sql1 = "SELECT * FROM user WHERE uname = ?";
        PreparedStatement pstmt1 = con.prepareStatement(sql1);
        pstmt1.setString(1,u.getUserName());
        ResultSet rs1 = pstmt1.executeQuery();
        rs1.next();
        UID = Integer.toString(rs1.getInt("uid"));
        pstmt1.close();
        //得到该用户的uid用来创建表↑
        String sql2 = "CREATE TABLE {0}" +
                "(fName   varchar(255) not null," +
                "content blob         not null," +
                "primary key (fName))";
        String Fname = "file_"+UID;
        Statement statement = con.createStatement();
        statement.execute(MessageFormat.format(sql2,Fname));
        statement.close();
        //创建新表
        System.out.println("成功");
        return result;
    }

/* 这是一个测试方法
    public static void main(String[] args){
        user u1 = new user();
        u1.setUserName("王起");
        u1.setPassword("123125");
        try {
            Connection con = DbUtil.getConnection();
            userDao userdao = new userDao();
            //userdao.register(con,u1);
            userdao.login(con,u1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

 */
}
