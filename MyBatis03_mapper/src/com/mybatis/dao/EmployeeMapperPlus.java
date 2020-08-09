package com.mybatis.dao;

import com.mybatis.bean.Employee;

import java.util.List;

/**
 * @version : 1.0
 * @auther : Firewine

 * @Program Name: <br>
 * @Create : 2018-11-19-20:21
 */
public interface EmployeeMapperPlus {


    public Employee getEmpById(Integer id);

    public Employee getEmpAndDept(Integer id);

    public Employee getEmpByIdStep(Integer id);

    public List<Employee> getEmpsByDeptId(Integer deptId);
}
