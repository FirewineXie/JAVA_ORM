package com.DBUtils.db;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-10-21:05
 */

import com.pratice.jdbc.JDBCTools;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试DButiles 工具类
 */
public class DBUtilsTest {

    /**
     * 把结果集转为一个数值（可以是任意基本数据类型）
     */
    @Test
    public void testScalarHandle(){
        Connection connection = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name,email,birth "+
                    "from customers where id = ?";

            Object customer =  queryRunner.query( connection,sql, new ScalarHandler<>(),5);

            System.out.println(customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }

    }

    @Test
    public  void testMapListHandle(){
        Connection connection = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name,email,birth "+
                    "from customers ";

          List<Map<String,Object>> customer =  queryRunner.query( connection,sql, new MapListHandler());

            System.out.println(customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }


    }

    /**
     * MapHandle 返回sql 对应的第一条记录对应的Map对象，
     * 建为sql查询的列名（不是别名）
     */
    @Test
    public  void  testMapHandle(){
        Connection connection = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name,email,birth "+
                    "from customers ";

            Map<String,Object> customer =  queryRunner.query( connection,sql, new MapHandler());

            System.out.println(customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }
    }
    /**
     * BeanListHandel 把结果集转为一个list，不为null，但可能为空集合
     * size（） 方法返回0
     * 若sql语句能够查询到记录，list中存放创建BeanListHandle 传入的Class
     * 对象对应的对象
     */
    @Test
    public void testBeanListHandle(){
        Connection connection = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name,email,birth "+
                    "from customers ";

            List<Customer> customer = (List<Customer>) queryRunner.query( connection,sql, new BeanHandler(Customer.class));

            System.out.println(customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }
    }
    /**
     * BeanHandle 把结果集的第一条记录转为创建BeanHandle 对象时传入的Class参数
     * 对应的对象
     */
    @Test
    public void testBeanHanlder(){
        Connection connection = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name,email,birth "+
                    "from customers where id >=?";

            Customer customer = (Customer) queryRunner.query(connection,sql,new BeanHandler(Customer.class),5);

            System.out.println(customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }
    }

    QueryRunner queryRunner = new QueryRunner();
    class  MyResultSEtHandler  implements  ResultSetHandler{

        @Override
        public Object handle(ResultSet resultSet) throws SQLException {


            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                Customer customer =
                        new Customer(id,name,email,birth);


                customers.add(customer);

            }
            return customers;
        }
    }
    @Test
    public void testQuery(){
        Connection connection = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name,email,birth "+
                    "from customers";
        Object obj =     queryRunner.query(connection,sql,new MyResultSEtHandler());

            System.out.println(obj);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }
    }
    /**
     * 测试QUerRunner 类的update
     * 该方法课用于insert，update和delete
     */
    @Test
    public  void testQueryRunnerUpdate(){

        //1.创建QueryRunner 的实现类
        QueryRunner queryRunner = new QueryRunner();
        //2.使用其update方法

        String sql = "delete from customers  "+
                "where id in (?,?)";
        Connection connection = null;

        try {
            connection = JDBCTools.getConnection();
            queryRunner.update(connection,sql,2,4);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }

    }
}
