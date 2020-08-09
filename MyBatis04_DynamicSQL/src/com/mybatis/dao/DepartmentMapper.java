package com.mybatis.dao;

import com.mybatis.bean.Department;

/**
 * @version : 1.0
 * @auther : Firewine

 * @Program Name: <br>
 * @Create : 2018-11-20-11:36
 */
public interface DepartmentMapper {

    public Department getDeptById(Integer id);

    public Department getDeptByIdPlus(Integer id);

    public Department getDeptByIdStep(Integer id);
}
