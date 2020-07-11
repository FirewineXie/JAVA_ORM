package com.test.dao;

import com.DBUtils.db.Customer;
import com.pratice.jdbc.JDBCTools;
import org.junit.Test;

import java.sql.Connection;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-11-16:14
 */
public class CustomerDaoTest {


    CustomerDao customerDao = new CustomerDao();
    @Test
    public void batch() {
    }

    @Test
    public void getForList() {
    }

    @Test
    public void get() {
        Connection connection = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "select id,name , "+
                    "email , birth from customers "+
                    "where id = ?";
            Customer customer = (Customer) customerDao.get(connection,sql,7);
            System.out.println(customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }
    }

    @Test
    public void update() {
    }

    @Test
    public void getForValue() {
    }
}