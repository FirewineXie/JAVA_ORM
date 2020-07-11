package sxt.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Firewine
 * @version : 1.0
 * @Program Name: Demo01
 * @Create : 2020/1/15
 * @Description :
 *
 * PreparedStatement 的使用
 */
public class Demo03 {

    public static void main(String[] args) {


        try{

            //加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立连接(连接对象内部其实包含了Socket对象，是一个远程的连接，比较耗时）
            //真正开发是使用，使用连接池，来管理连接的
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=UTC?serverTimezone=UTC", "root", "123456");


            //可以使用占位符，避免sql注入
            String sql = "insert into t_user (username,pwd,regTime) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            //参数索引从1 开始计算
            ps.setString(1, "ceshi");
            ps.setString(2, "123456");
            //可以交给程序去判断，不用后面去判断
            ps.setObject(1, "ceshi1");
            ps.setObject(2, "12345676");
            ps.setObject(3, new java.sql.Date(System.currentTimeMillis()));

            ps.execute();
            System.out.println("插入一行记录 ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
