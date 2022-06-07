package com.JavaPro.dao;

import com.JavaPro.model.planFile;
import com.JavaPro.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.io.*;
import java.util.ArrayList;

/**
 * @describe 计划文件Dao类,拥有文件插入和文件查询的功能
 * @author: 周文瑞 20373804
 */
public class planFileDao {

    /**
     *  读取登录用户存在数据库所有的文件planFile
     * @param con 连接数据库的Connection类
     * @param u 登录用户实例,存储着用户信息
     * @throws Exception
     * @return 返回登录用户的所有文件列表,设置了文件名字和文件内容,(以byte[]存储,最大为65536)
     */
    public ArrayList<planFile> getAllPlanFile(Connection con, user u)throws Exception{
        ArrayList<planFile> fileList = new ArrayList<>();
        String fileName = "file_" + Integer.toString(u.getUid());
        String sql = "SELECT * FROM {0}";
        Statement statement = con.createStatement();
        ResultSet rs =  statement.executeQuery(MessageFormat.format(sql,fileName));
        while(rs.next()){
            planFile newfile = new planFile();
            byte[] newContent = new byte[65536];
            newfile.setfName(rs.getString("fName"));
            InputStream in = rs.getBinaryStream("content");
            in.read(newContent);
            newfile.setContent(newContent);
            fileList.add(newfile);
            in.close();
        }
        rs.close();//释放资源
        statement.close();//释放资源
        return fileList;
    }

    /**
     * 向file表中插入一个文件
     * @param con 连接数据库的Connection类
     * @param u 登录用户实例,存储着用户信息
     * @param f 文件实例,在本函数中需要初始化fName和file
     * @throws Exception
     */
    public void insertPlanFile(Connection con, user u,planFile f)throws Exception{
        String fileName = "file_" + Integer.toString(u.getUid());
        String sql = "INSERT INTO "+fileName+"(fName,content) VALUES(?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,f.getfName());
        InputStream in = new FileInputStream(f.getFile());
        pstmt.setBinaryStream(2,in,(int)f.getFile().length());
        int result = pstmt.executeUpdate();
        if( result > 0 )
        {
            System.out.println("文件写入成功");
        }else{
            System.out.println("文件写入失败");
        }
        in.close();
        pstmt.close();
    }

    /**
     * 更新数据库中file表的文件
     * @param con 连接数据库的Connection类
     * @param u 登录用户实例,存储着用户信息
     * @param f 文件实例,在本函数中需要初始化fName和file
     * @throws Exception
     */
    public void updatePlanFile(Connection con, user u,planFile f)throws Exception{
        String fileName = "file_" + Integer.toString(u.getUid());
        String sql = "UPDATE "+fileName+" SET content = ? WHERE fName = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        InputStream in = new FileInputStream(f.getFile());
        pstmt.setBinaryStream(1,in,(int)f.getFile().length());
        pstmt.setString(2,f.getfName());
        int result = pstmt.executeUpdate();
        if( result > 0 )
        {
            System.out.println("文件修改成功");
        }else{
            System.out.println("文件修改失败");
        }
        in.close();
        pstmt.close();
    }
}
