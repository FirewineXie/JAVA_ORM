package com.mybatis.dao;

import com.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * @version : 1.0
 * @auther : Firewine
 * @email : 1451661318@qq.com
 * @Program Name: <br>
 * @Create : 2018-11-19-10:06
 */
public interface EmployeeMapperAnnotation {

    @Select("select * from tb1_employee where id=#{id}")
    public Employee getEmpById(Integer id);
}
