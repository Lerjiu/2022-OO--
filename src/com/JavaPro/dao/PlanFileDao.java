package com.JavaPro.dao;

import com.JavaPro.model.PlanFile;
import com.JavaPro.model.User;

import java.sql.*;
import java.text.MessageFormat;
import java.io.*;
import java.util.ArrayList;

/**
 * @describe 计划文件Dao类,拥有文件插入和文件查询的功能
 * @author: 周文瑞 20373804
 */
public class PlanFileDao {
    private static UserDao userDao = new UserDao();

    /**
     *  读取登录用户存在数据库所有的文件planFile
     * @param con 连接数据库的Connection类
     * @param u 登录用户实例,存储着用户信息
     * @throws Exception
     * @return 返回登录用户的所有文件列表,设置了文件名字和文件内容,(以byte[]存储,最大为65536)
     */
    public ArrayList<PlanFile> getAllPlanFile(Connection con, User u)throws Exception{
        ArrayList<PlanFile> fileList = new ArrayList<>();
        int uid = userDao.getUidByName(con, u.getUserName());
        String fileName = "file_" + uid;
        String sql = "SELECT * FROM {0}";
        Statement statement = con.createStatement();
        ResultSet rs =  statement.executeQuery(MessageFormat.format(sql,fileName));
        while(rs.next()){
            PlanFile newfile = new PlanFile();
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
    public boolean insertPlanFile(Connection con, User u, PlanFile f)throws Exception{
        int uid = userDao.getUidByName(con, u.getUserName());
        String fileName = "file_" + uid;
        String sql = "INSERT INTO "+fileName+"(fName,content) VALUES(?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,f.getfName());
        InputStream in = new FileInputStream(f.getFile());
        pstmt.setBinaryStream(2,in,(int)f.getFile().length());
        int result = pstmt.executeUpdate();
        in.close();
        pstmt.close();

        if( result > 0 )
        {
            System.out.println("文件写入成功");
            return true;
        }else{
            System.out.println("文件写入失败");
            return false;
        }
    }

    /**
     * 更新数据库中file表的文件
     * @param con 连接数据库的Connection类
     * @param u 登录用户实例,存储着用户信息
     * @param f 文件实例,在本函数中需要初始化fName和file
     * @throws Exception
     */
    public void updatePlanFile(Connection con, User u, PlanFile f)throws Exception {
        int uid = userDao.getUidByName(con, u.getUserName());
        String fileName = "file_" + uid;
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

    public boolean planFileExist(Connection con, User u, PlanFile f) throws Exception {
        int uid = userDao.getUidByName(con, u.getUserName());
        String fileName = "file_" + uid;
        String sql = "SELECT * FROM "+fileName+" WHERE fName = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,f.getfName());
        ResultSet rs = pstmt.executeQuery();
        if( rs.next() )
        {
            rs.close();
            pstmt.close();
            return true;
        }
        rs.close();
        pstmt.close();
        return  false;
    }
}
