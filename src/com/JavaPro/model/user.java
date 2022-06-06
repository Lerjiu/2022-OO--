package com.JavaPro.model;

/**
 * 用户实体
 * @author: 周文瑞_20373804
 *
 */
public class user {
    private int uid;// 编号
    private String userName; // 名字
    private String password; //密码

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
