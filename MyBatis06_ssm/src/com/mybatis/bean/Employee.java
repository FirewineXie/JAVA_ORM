package com.mybatis.bean;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @version : 1.0
 * @auther : Firewine

 * @Program Name: <br>
 * @Create : 2018-11-18-15:21
 */
@Alias("employee")
public class Employee implements Serializable {
    //继承接口是序列化的调用

    private Integer id;
    private String lastName;
    private String email;
    private String gender;
    private Department dept;

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }




    public Employee() {
        super();
    }



    public Employee(Integer id, String lastName, String email, String gender,
                    Department dept) {
        super();
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dept = dept;
    }



    public Employee(Integer id, String lastName, String email, String gender) {
        super();
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }


    @Override
    public String toString() {
        return "Employee [id=" + id + ", lastName=" + lastName + ", email="
                + email + ", gender=" + gender + "]";
    }
}
