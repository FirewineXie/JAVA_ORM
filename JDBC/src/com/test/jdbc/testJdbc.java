package com.test.jdbc;

import com.pratice.jdbc.JDBCTools;

import javax.xml.transform.Result;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-09-29-23:50
 */
public class testJdbc {
    /**
     * 执行sql语句，使用PreparedStatement
     * 可以任何添加，修改
     * @param sql
     * @param args 填写sql 站位符的可变阐述
     */
    public static void update(String sql,Object ... args){
            Connection connection = null;
            PreparedStatement preparedStatement =null;

            try{
                connection = testJdbc.getConnection();
                preparedStatement = connection.prepareStatement(sql);

                for (int i=0;i< args.length;i++){
                    preparedStatement.setObject(i+1,args[i]);
                }

                preparedStatement.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                testJdbc.closeConnection(null,preparedStatement,connection);
            }
    }
    /**
     * 处理更新，删除，修改的语句方法
     */
    public static void update(String sql){
        Connection conn = null;
        Statement statement = null;

        try{
            conn = JDBCTools.getConnection();
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (statement != null){
                try{
                    statement.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (conn != null){
                try{
                    conn.close();
                }catch (Exception e2){
                    e2.printStackTrace();
                }
            }
        }
    }
    /**
     * 查询语句的方法
     *
     * @param sql
     */
    public static void ResultSet(String sql){
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            conn  = JDBCTools.getConnection();
            statement = conn.createStatement();


            rs  =statement.executeQuery(sql);

            if (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString("name");
                Date birth = rs.getDate(4);

                System.out.println(id);
                System.out.println(name);
                System.out.println(birth);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(rs,statement,conn);
        }
    }
    /**
     * 连接数据库的
     * @return
     * @throws Exception
     */
    public static Connection getConnection()throws Exception{
        /*
            首先读取配置文件，然后加载驱动，然后利用Drivermanager 进行连接
         */

        Properties ppop = new Properties();

        InputStream in = testJdbc.class.getClassLoader().getResourceAsStream("jdbc.properties");


        ppop.load(in);


        String user = ppop.getProperty("user");
        String password = ppop.getProperty("password");
        String jdbcUrl = ppop.getProperty("jdbcUrl");
        String driver = ppop.getProperty("driver");


        Class.forName(driver);

        return DriverManager.getConnection(jdbcUrl,user,password);


    }

    /**
     * 除了查询以外的语句，，关闭情况
     */
    public static void closeConnection(Statement statement,Connection connection){
        if (statement != null){
            try{
                statement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (connection != null){
            try{
                connection.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    /**
     * 关闭查询连接，释放数据库资源的方法
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void closeConnection(ResultSet resultSet,Statement statement,Connection connection){
        if (resultSet != null){
            try{
                resultSet.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (statement != null){
            try{
                statement.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (connection != null){
            try{
                connection.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
}
