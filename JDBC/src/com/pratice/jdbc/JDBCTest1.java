package com.pratice.jdbc;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-09-19:07
 */
public class JDBCTest1 {

    /**
     * 读取blob数据：
     * 1.使用getBlob方法读取到Blob对象
     * 2.调用blob的getBinaryStream得到输入流，在io操作
     */
    @Test
    public void readBlob(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name customername,email,birth,picture "+
                    "From customers where id = 8 ";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            //1.得到ResultSetMetaDat对象
            ResultSetMetaData rsmd = resultSet.getMetaData();

            if (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);

                String email = resultSet.getString(3);

                System.out.println(id + ", " +  name + " ," + email);

                Blob picture = resultSet.getBlob(4);
                InputStream in = picture.getBinaryStream();

                OutputStream out = new FileOutputStream("flower.jsp");

                byte [] buffer = new byte[1024];

                int len=0;

                while ((len = in.read(buffer)) != -1){
                    out.write(buffer,0,len);
                }

                out.close();
                in.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,preparedStatement,connection);
        }
    }

    /**
     * 插入BLOB类型的数据必须使用PreParedStatement，因为Blob类型，
     * 无法使用字符串拼写的
     *
     *
     * 调用setBlob（int index，INputSetream inputStream）
     */
    @Test
    public void testInsertBlob(){
        Connection connection = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "insert into customers(name,email,birth,picture)"+
                    "Values(?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"abcde");
            preparedStatement.setString(2,"ancee@11.com");
            preparedStatement.setDate(3,new Date(new java.util.Date().getTime()));

            InputStream inputStream = new FileInputStream("D:\\IdeaProjects\\JDBC\\18031.jpg");
            preparedStatement.setBlob(4,inputStream);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,preparedStatement,connection);
        }
    }
    /**
     * 取得数据库自动生成的主键
     */
    @Test
    public void testGetKeyValue(){

        Connection connection = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "insert into customers(name,email,birth)"+
                        "Values(?,?,?)";

//            preparedStatement = connection.prepareStatement(sql);
            /**
             * 使用重载prepareStatement（SQL，tag）
             */
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,"abcde");
            preparedStatement.setString(2,"ancee@11.com");
            preparedStatement.setDate(3,new Date(new java.util.Date().getTime()));

            preparedStatement.executeUpdate();
            //通过getGenreatedKeys 获取包含了新生成的主键的ResultSet对象
            //在Ressultset 只有一列GENERATED_KEY 用于存放新生成的主键
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                System.out.println(resultSet.getObject(1));
            }
            ResultSetMetaData rsmd = resultSet.getMetaData();
            for (int i=0;i<rsmd.getColumnCount();i++){
                System.out.println(rsmd.getColumnName(i+1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,preparedStatement,connection);
        }
    }
}
