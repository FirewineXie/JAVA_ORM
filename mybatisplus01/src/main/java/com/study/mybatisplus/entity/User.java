package com.study.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Firewine
 * @version 1.0
 * @ProgramName: User
 * @Create 2020/8/5
 * @Description: 因为每个基本类型都有一个默认值:
 *  * 	   int ==> 0
 *  * 	   boolean ==> false
 */
/*
 * MybatisPlus会默认使用实体类的类名到数据中找对应的表.
 *
 */
//@TableName(value="tbl_employee")
@Data
@Accessors(chain = true)
public class User {

    //@TableName(value="tbl_employee")
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private Integer age;
    private String email;
    @TableField(exist = false)
    private String ignoreColumn = "ignoreColumn";

    @TableField(exist = false)
    private Integer count;
}