package com.pratice.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-09-20:33
 */
public class TransactionTest {

    /**
     * 测试事务的隔离级别
     * 在jdbc 程序中通过Connection的setTransactionIsolation
     * 来设置事务的隔离级别
     */
    @Test
    public void testTransactionIsolationUpdate(){

        Connection connection = null;

        try{
            connection = JDBCTools.getConnection();
            connection.setAutoCommit(false);

            String sql = "update users set balance = "+
                        "balance - 500 where id =1";
            update(connection,sql);

            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }
    }
    @Test
    public void testTransactionIsolationRead(){
        String sql = "select balance from users where id =1";
        Integer balance = getForValue(sql);
        System.out.println(balance);

    }
    //返回某条记录的某一个字段的值或一个统计的值（一共有多少条记录等）
    public <E> E getForValue(String sql, Object... args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            //1.得到结果集：该结果集应该只有一行，且只有一列
            connection = JDBCTools.getConnection();
//            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return (E) resultSet.getObject(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,preparedStatement,connection);
        }
        //2.取得结果集
        return null;
    }

    /**
     * tom给jerry汇款500
     *
     *
     * 关于事务；
     * 1如果多个操作，每个操作使用的是自己的单独连接，则无法保证事务
     * 2.具体步骤：
     * 1）事务操作开始前，  开始事务：取消Connection的默认提交行文
     * setAUtoCOmmit（false）
     *
     * 2） 事务操作成功，，则提交事务
     * 3）回滚事务，如果事务错误，在catch中回滚事务
     */
    @Test
    public void testTransaction(){


        Connection connection = null;
        try{
            connection = JDBCTools.getConnection();
            //开始事务：取消默认提交
            connection.setAutoCommit(false);
            String sql = "update users set balance ="+
               "balance - 500 where id = 1";
            update(connection,sql);
            int i = 10 /0;
            System.out.println(i);

        sql = "update users set balance ="+
                "balance + 500 where id = 2";

        update(connection,sql);
        }catch (Exception e){
            e.printStackTrace();

            //回滚事务
            try {
                connection.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }finally {
            JDBCTools.release(null,null,connection);
        }
//
//        DAO dao = new DAO();
//
//        String sql = "update users set balance ="+
//                "balance - 500 where id = 1";
//        dao.update(sql);
//
//        sql = "update users set balance ="+
//                "balance + 500 where id = 2";
//
//        dao.update(sql);
    }
    public void update(Connection connection ,String sql,Object ... args){

        PreparedStatement preparedStatement = null;

        try{

            preparedStatement = connection.prepareStatement(sql);


            for (int i=0;i< args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,preparedStatement,connection);
        }
    }
}
