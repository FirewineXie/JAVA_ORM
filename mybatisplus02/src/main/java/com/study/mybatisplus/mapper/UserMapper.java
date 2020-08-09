package com.study.mybatisplus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.mybatisplus.entity.User;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Firewine
 * @version 1.0
 * @ProgramName: UserMapper
 * @Create 2020/8/5
 * @Description : MP 支持不需要 UserMapper.xml 这个模块演示内置 CRUD 咱们就不要 XML 部分了
 */

public interface UserMapper extends BaseMapper<User> {

}
