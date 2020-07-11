package com.pratice.jdbc;



/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-09-29-21:30
 */


import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 操作JDBC的工具类，其中分装一些工具方法
 *
 */
public class JDBCTools {
    public static void release(ResultSet rs, Statement statement, Connection conn){
        if (rs != null){
            try{
                rs.close();
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
        if (conn != null){
            try{
                //也只是返回连接池中，不是真的关闭了

                conn.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }

    }
    /**
     * 关闭statement和connection 的方法
     * @param statement
     * @param conn
     */
    public static void release(Statement statement,Connection conn){
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
    /**
     * 1.获取连接的方法
     * 通过读取配置文件从数据库服务器获取一个连接
     * @return
     * @throws Exception
     */
    private static DataSource dataSource = null;
    //数据库连接池只被初始化一次。
    static {
        dataSource = new ComboPooledDataSource("helloc3p0");
    }
    public static Connection getConnection()throws Exception{

        return dataSource.getConnection();
    }
//    public static Connection getConnection()throws Exception{
//        //1.准备连接数据库的4个字符串
//        //2.获取jdbc.properties对应的输入流
//        Properties ppop = new Properties();
//
//        //3.加载对应的输入流
//        InputStream in =
//                JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
//
//        //4.具体决定user。password的等4个个字符串
//        ppop.load(in);
//        //3.加载数据库驱动程序（对应的Driver实现类中有注册驱动的静态代码块）
//        String user = ppop.getProperty("user");
//        String password = ppop.getProperty("password");
//        String jdbcUrl = ppop.getProperty("jdbcUrl");
//        String driver = ppop.getProperty("driver");
//
//        Class.forName(driver);
//        //4.通过DriverManager 的GetConnection方法获取数据库连接
//        return DriverManager.getConnection(jdbcUrl,user,password);
//    }
}
