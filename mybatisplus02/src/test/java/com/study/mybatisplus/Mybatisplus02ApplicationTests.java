package com.study.mybatisplus;

import com.study.mybatisplus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Mybatisplus02ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public  void testARInsert(){

        User user  =  new User();
        user.setName("cccc");
        user.setEmail("aa@.com");
        user.setAge(20);

        boolean insert = user.insert();
        System.out.println(insert);
    }
}
