package com.mybatis.service;

import com.mybatis.bean.Employee;
import com.mybatis.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version : 1.0
 * @auther : Firewine

 * @ProgramName: <br>
 * @Create : 2018-11-23-14:38
 * @Description : <br/>
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getEmps(){

        return employeeMapper.getEmps();
    }
}
