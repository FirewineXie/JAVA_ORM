package com.page.dao;

import com.page.bean.Role;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface RoleMapper {



    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll(RowBounds rowBounds);

    int updateByPrimaryKey(Role record);
}