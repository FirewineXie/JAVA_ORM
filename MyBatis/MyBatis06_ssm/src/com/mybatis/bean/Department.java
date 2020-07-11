package com.mybatis.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @version : 1.0
 * @auther : Firewine
 * @email : 1451661318@qq.com
 * @Program Name: <br>
 * @Create : 2018-11-19-20:42
 */
public class Department implements Serializable {

    private Integer id;
    private String departmentName;
    private List<Employee> emps;

    public Department(int i) {
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    public Department(Integer id, String departmentName, List<Employee> emps) {
        this.id = id;
        this.departmentName = departmentName;
        this.emps = emps;
    }

    public Department(String s) {
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", emps=" + emps +
                '}';
    }
}
