package com.pratice.jdbc;

import org.junit.Test;

import java.util.Date;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-02-12:01
 */
public class DAOTest {

    DAO dao = new DAO();
    @Test
    public void update() {
        String sql = "insert into customers(name ,"+
        "email,birth) values(?,?,?)";
        dao.update(sql,"xiaoming","xiaoming@143.com",new Date(new java.util.Date().getTime()));
    }

    @Test
    public void get() {
        String sql = "select flow_id flowId,type, exam_card examCard,"+
                "id_card idCard,student_name studentName,location,"+
                "grade from examstudent where flow_id= ?";

        Student student = dao.get(Student.class,sql,5);
    }

    @Test
    public void getForList() {
    }

    @Test
    public void getForValue() {
    }
}