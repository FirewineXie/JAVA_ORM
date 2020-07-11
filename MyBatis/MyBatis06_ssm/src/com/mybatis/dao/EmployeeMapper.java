package com.mybatis.dao;

import com.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @version : 1.0
 * @auther : Firewine
 * @email : 1451661318@qq.com
 * @Program Name: <br>
 * @Create : 2018-11-18-16:06
 */
public interface EmployeeMapper {


    public Employee getEmpById(Integer id);

    public List<Employee> getEmps();


}
