package com.study.mybatisplus.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Firewine
 * @version 1.0
 * @ProgramName: MybatisPlusConfig
 * @Create 2020/8/5
 * @Description:
 */
@Configuration
@MapperScan("com.study.mybatisplus.mapper")
public class MybatisPlusConfig {



}