package sxt.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author : Firewine
 * @version : 1.0
 * @Program Name: Demo01
 * @Create : 2020/1/15
 * @Description :
 */
public class Demo01 {

    public static void main(String[] args) {


        try{

            //加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立连接(连接对象内部其实包含了Socket对象，是一个远程的连接，比较耗时）
            //真正开发是使用，使用连接池，来管理连接的
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=UTC?serverTimezone=UTC", "root", "123456");


            System.out.println(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
