package com.pratice.jdbc;

import org.junit.Test;

import java.sql.*;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-09-17:37
 */
public class MetaDataTest {
    /**
     * ResultSetMetaData 描述结果集的元数据
     * 可以得到结果集的基本信息，：结果集有哪些列，列名，列的别名等
     * 结合反射可以写出通用的查询方法
     */
    @Test
    public void testResultSetMetaData(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name customername,email,birth "+
                        "From customers";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            //1.得到ResultSetMetaDat对象
            ResultSetMetaData rsmd = resultSet.getMetaData();

            //2.得到列的个数
            int columnCount = rsmd.getColumnCount();
            System.out.println(columnCount);

            for (int i = 0;i < columnCount; i++){
                //3.得到列名
                String columnName = rsmd.getColumnName(i +1);

                String columnLabel = rsmd.getColumnLabel(i +1);

                System.out.println(columnName + " : "+ columnLabel);
            }


            //4.得到列的别名
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,preparedStatement,connection);
        }
    }
    /**
     * DatabaseMetaData 是描述数据库的元数据对象，
     * 可以由Connection得到
     */
    @Test
    public void testDatabaseMetaData(){

        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCTools.getConnection();

            DatabaseMetaData data = connection.getMetaData();

            //可以得到数据库本身的一些信息
            //得到数据库的版本号
            int version = data.getDatabaseMajorVersion();
            System.out.println(version);

            String user = data.getUserName();
            System.out.println(user);

            //mysql 有那些数据库
            resultSet = data.getCatalogs();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,null,connection);
        }

    }
}
