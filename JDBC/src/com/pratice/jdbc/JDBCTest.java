package com.pratice.jdbc;


import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-09-29-10:23
 */
public class JDBCTest {//面向接口编程
    /**
     * ResultSet ：结果集 封装了使用JDBC ，进行查询的结果
     * 1.调用Statement 对象的executeQuery 可以得到结果集
     * 2.ResultSet 返回的实际上就是一个数据表，有一个指针指向数据表的第一行的前面
     * 可以调用next方法检测下一行是否有效，若有效返回true 且指针下移
     * hasNext 和next的方法的结和体 Itorator对象
     * 3. 当指针到一行时，可以通过地阿偶欧诺个getXXX（index） 或getXXX（columnName）
     * 获取每一列的值
     * 4、resultset 也需要关闭
     */
    @Test
    public void testResultSet(){
        //获取id=4 的customers 数据表的记录 并打印

        //1.获取Connection
        //2.获取Statement
        //3.准备sql
        //4.执行查询，得到Resultset
        //5.处理Resultset
        //6.关闭
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            conn  =JDBCTools.getConnection();
            statement = conn.createStatement();

            String sql = "select *"+
                        "from customers ";

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
     * 通用的更新的方法，包括insert，UPDATE，delete
     * 版本1.
     */
    public void update(String sql){
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
     * 想指定数据表中插入一条记录，通过jdbc
     * 1.statement 用于指定sql 语句的对象
     * 1）通过Connection 的CreatStatement 方法获取
     * 2）通过executeupdate 可以执行SQL语句
     * 3）传入的sql可以是insert UPDATE 或delete  但是select是不能的
     *
     * 2.Connection，statement都是应用程序和数据库服务器的连接资源，要关闭
     * 3. 不管怎么样，都要关闭，可以finally
     *
     *
     * 4.关闭的顺序；先关闭后获取的，即关闭statement 后关闭Connection
     */
    @Test
    public void testStatement()throws  Exception{
        //1.获取数据库连接
        Connection conn = null;
        Statement statement =null;


        try {
            conn = getConnection2();
            //2.准备插入sql语句
            String sql = null;
            //"INSERT INTO CUSTOMERS (NAME,email,birth)"+
            //      "values('XYZ','XYZ@guigu.com','2880-12-12');";
            //sql = "delete from customers where id =1 ";
            sql = "update customers set name ='Tom'"+
                    "where  id = 2";
            //3.执行插入
            //1)获取sql语句的statement 对象，调用connection的createStatemnt方法啦获取
            statement = conn.createStatement();
            //2) 调用Statement 的executeUpdate（sql） 执行
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            try {
                //关闭statement对象
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                //4.关闭连接
                if (conn != null)
                    conn.close();
            }

        }


    }
    @Test
    public  void testGetConnection2()throws Exception{
        System.out.println(getConnection());
    }
    public Connection getConnection2()throws Exception{
        //1.准备连接数据库的4个字符串
        //2.获取jdbc.properties对应的输入流
        Properties ppop = new Properties();

        //3.加载对应的输入流
        InputStream in =
                this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");

        //4.具体决定user。password的等4个个字符串
        ppop.load(in);
        //3.加载数据库驱动程序（对应的Driver实现类中有注册驱动的静态代码块）
        String user = ppop.getProperty("user");
        String password = ppop.getProperty("password");
        String jdbcUrl = ppop.getProperty("jdbcUrl");
        String driver = ppop.getProperty("driver");

        Class.forName(driver);
        //4.通过DriverManager 的GetConnection方法获取数据库连接
        return DriverManager.getConnection(jdbcUrl,user,password);
    }
    /**
     * Drivermanage 是驱动的管理类
     * 1） 可以通过抽杆夹你再的getConnection 方法获取数据库连接较为方便的
     * 2）可以同时管理多个驱动程序：若注册了多个数据库的连接，则调用getconnection 方法
     * 传入的参数不同，即返回不同的数据库连接
     */
    @Test
    public void  testDriverManage()throws Exception{
        //1.连接数据库4个字符串
        //驱动，url，user，password
        String driverClass = null;
        String jdbcurl= null;
        String user = null;
        String password = null;
        //读取类路径的配置文件
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(in);
        driverClass = properties.getProperty("driver");
        jdbcurl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");


        Driver driver = (Driver)Class.forName(driverClass).newInstance();
        //反射获得类
        Properties info = new Properties();
        info.put("user",user);
        info.put("password",password);

        //2.加载数据库驱动（对应的driver 实现类中有注册驱动的静态代码块）
        //可以注册多个驱动程序
        Class.forName(driverClass);
        //通过drivermanage 的getConnection 方法连接
        Connection connection =
                    DriverManager.getConnection(jdbcurl,user,password);
        System.out.println(connection);
    }
    /**
     * driver 是一个接口：数据库厂商必须事项的接口，能够从中获取数据库连接
     *可以通过driver的实现类独享获取数据库连接
     * 1.加入mysql驱动
     * 2.
     */
   // @Test
    public void testDriver()throws SQLException {
        //1创建一个driver实现类的独享
        Driver driver = new com.mysql.cj.jdbc.Driver();
        //2.准备登录信息
        String url ="jdbc:mysql://localhost:3306/test?useSSL=true";//三部分：协议，子协议  子名称
        Properties info = new Properties();

        info.put("user","root");
        info.put("password","123456");
        //  3.调用用driver接口的 connection url，info 获取数据库连接
        Connection connection = driver.connect(url,info);

        System.out.println(connection);
    }

    /**
     * 编写一个通用的方法，在不修改源程序的修改下，可以获取任何数据库的连接
     * 解决方法：把数据库的驱动和登录信息放入一个配置文件里面
     * 通过修改可以实现具体的数据库解耦合
     */
    public Connection getConnection()throws Exception{
        String driverClass = null;
        String jdbcurl= null;
        String user = null;
        String password = null;
        //读取类路径的配置文件
        InputStream in =
             getClass().getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(in);
        driverClass = properties.getProperty("driver");
        jdbcurl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");


        Driver driver = (Driver)Class.forName(driverClass).newInstance();
        //反射获得类
        Properties info = new Properties();
        info.put("user",user);
        info.put("password",password);
        Connection connection = driver.connect(jdbcurl,info);

        return connection;
    }
    @Test
    public void testGetConnection() throws Exception{
        System.out.println(getConnection());
    }
}
