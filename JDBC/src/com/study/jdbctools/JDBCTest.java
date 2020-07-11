package com.study.jdbctools;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pratice.jdbc.JDBCTools;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author Firewine
 * @version : 1.0
 * @Program Name: <br>
 * @Create : 2018-10-09-22:43
 */
public class JDBCTest {

    @Test
    public void testjdbcTools() throws Exception {
        Connection connection = JDBCTools.getConnection();
        System.out.println(connection);
    }
    /**
     * 创建c3p0 -config .xml 文件， 参考帮助文档中的信息
     * 创建ComboPooleDataSource 实例
     *  DataSource dataSource =
     *                 new ComboPooledDataSource("helloc3p0");
     *
     * 3 。从dateSource 获取数据库连接
     * @throws SQLException
     */
    @Test
    public void testC3p0WithCOnfigFile() throws SQLException {
        DataSource dataSource =
                new ComboPooledDataSource("helloc3p0");


        System.out.println(dataSource.getConnection());
        ComboPooledDataSource comboPooledDataSource =
                (ComboPooledDataSource) dataSource;

        System.out.println(comboPooledDataSource.getMaxPoolSize());

    }



    /**
     *
     */
    @Test
    public void testC3P0() throws PropertyVetoException, SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setUser("root");
        cpds.setPassword("123456");
        cpds.setJdbcUrl("jdbc:mysql:///runoob?useSSL=true");
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");

        System.out.println(cpds.getConnection());



    }

    /**
     * 1.加载dbcp 的properties 的配置文件，：配置文件中的键来自BasicDataSource的属性
     * 2.调用 BaicDataSourceFactory 的createDataSource 方法创建DataSource实例
     * 3.从DataSource 实例中获取数据库连接，
     * @throws Exception
     */
    @Test
    public void testDBCPWithDataSourceFactory() throws Exception {

        Properties properties  = new Properties();
        InputStream instream = JDBCTest.class.getClassLoader()
                .getResourceAsStream("com/study/jdbctools/dbcp.properties");
        properties.load(instream);

        DataSource dataSource = BasicDataSourceFactory
                .createDataSource(properties);

        System.out.println(dataSource.getConnection());


//        BasicDataSource basicDataSource = (BasicDataSource) dataSource;
//
//        System.out.println(basicDataSource.getMaxTotal());

    }

    /**
     * 使用DBCP连接池
     *
     * 1.加入jar包，依赖于logging，pools 还有ssl必须是true 因为mysql是5.版本的
     * 2.创建数据库连接池
     * 3.为数据库实例指定必须的属性
     * 4.从数据源中获取即数据连接
     */

    @Test
    public void testDBCP() throws SQLException {
        BasicDataSource dateSource = null;
        //1.创建DBCP 数据源实例
        dateSource = new BasicDataSource();

        //2.为数据源实例指定必须的属性
        ((BasicDataSource) dateSource).setUsername("root");
        ((BasicDataSource) dateSource).setPassword("123456");
        ((BasicDataSource) dateSource).setUrl("jdbc:mysql:///runoob?useSSL=true");
        ((BasicDataSource) dateSource).setDriverClassName("com.mysql.cj.jdbc.Driver");

        //3。指定数据源的一些属性
        //1)指定数据库连接池中初始化连接池个数
        dateSource.setInitialSize(5);

        //2)指定最大的连接数:同一时刻可以同时向数据申请的连接数
        dateSource.setMaxIdle(5);

        //3)指定最小连接数：空闲状态下，数据池中保存的最少的空闲连接的数量
        dateSource.setMinIdle(5);

        //4)等待数据库连接分配的最长时间，单位为毫秒，超出该时间，将抛出异常
        dateSource.setMaxTotal(1000 * 5);
        //4.从数据源中获取数据库连接
        Connection connection =dateSource.getConnection();
        System.out.println("1"+connection.getClass());



        connection =dateSource.getConnection();
        System.out.println("2"+connection.getClass());

        connection =dateSource.getConnection();
        System.out.println("3"+connection.getClass());

        connection =dateSource.getConnection();
        System.out.println("4"+connection.getClass());

        connection =dateSource.getConnection();
        System.out.println("5"+connection.getClass());

        Connection connection2 =dateSource.getConnection();
        System.out.println("6"+connection2.getClass());


        BasicDataSource finalDateSource = dateSource;
        new Thread(){
            @Override
            public void run() {
                Connection connection1;
                try{
                    connection1 = finalDateSource.getConnection();
                    System.out.println(connection1.getClass());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();

        try{
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        connection2.close();
    }
}
