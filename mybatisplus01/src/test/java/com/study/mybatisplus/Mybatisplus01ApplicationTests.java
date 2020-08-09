package com.study.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.mybatisplus.entity.User;
import com.study.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
@SpringBootTest
class Mybatisplus01ApplicationTests {

    @Resource
    private UserMapper mapper;

    @Test
    public void aInsert() {
        User user = new User();
        user.setName("小羊");
        user.setAge(3);
        user.setEmail("abc@mp.com");
        mapper.insert(user);
        // 成功直接拿会写的 ID
        System.out.println(user.getId());
    }

    @Test
    public void bDelete() {
        mapper.deleteById(1);
        mapper.delete(new QueryWrapper<User>()
                .lambda().eq(User::getName, "Sandy"));
    }

    @Test
    public void cUpdate(){
        User user = new User();
        user.setId(2);
        user.setAge(18);
        user.setEmail("aa@qq.com");
        user.setName("消炎");
        mapper.updateById(user);
        mapper.update(user, new UpdateWrapper<User>().lambda().eq(User::getName, "Jack"));
    }
    @Test
    public void dSelect(){
        //  1. 根据id 查找
        User user = mapper.selectById(2);
        System.out.println(user.toString());

        // 2. 根据name 或者其他属性查找，出一个数据
        User user1 = mapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 10086));
        System.out.println(user1.toString());

        mapper.selectList(Wrappers.<User>lambdaQuery().select(User::getId))
                .forEach(x ->{
                    x.getId();
                    x.getAge();
                });
        mapper.selectList(new QueryWrapper<User>().select("id", "name"))
                .forEach(x -> {
                    x.getId();
                    x.getEmail();
                    x.getName();
                    x.getAge();
                });
        mapper.selectList(Wrappers.lambdaQuery(new User().setId(1)));
    }

    /**
     * 排序根据提供变量
     */
    @Test
    public void orderBy(){
        List<User> users = mapper.selectList(Wrappers.<User>query().orderByAsc("age"));
        System.out.println(users);
    }

    /**
     * 根据age排序，list
     */
    @Test
    public void selectMaps() {
        List<Map<String, Object>> mapList = mapper.selectMaps(Wrappers.<User>query().orderByAsc("age"));
        System.out.println(mapList);
    }

    /**
     * 分页查找
     */
    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page = mapper.selectMapsPage(new Page<>(1, 5), Wrappers.<User>query().orderByAsc("age"));
        System.out.println(page.getRecords().get(0));
    }


    @Test
    public void orderByLambda() {
        List<User> users = mapper.selectList(Wrappers.<User>lambdaQuery().orderByAsc(User::getAge));
        System.out.println(users);
    }

    @Test
    public void testSelectMaxId() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("max(id) as id");
        User user = mapper.selectOne(wrapper);
        System.out.println("maxId=" + user.getId());
        List<User> users = mapper.selectList(Wrappers.<User>lambdaQuery().orderByDesc(User::getId));
        Assert.assertEquals(user.getId(), users.get(0).getId());
    }

    @Test
    public void testGroup() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("age, count(*)")
                .groupBy("age");
        List<Map<String, Object>> maplist = mapper.selectMaps(wrapper);
        for (Map<String, Object> mp : maplist) {
            System.out.println(mp);
        }
        /**
         * lambdaQueryWrapper groupBy orderBy
         */
        LambdaQueryWrapper<User> lambdaQueryWrapper = new QueryWrapper<User>().lambda()
                .select(User::getAge)
                .groupBy(User::getAge)
                .orderByAsc(User::getAge);
        for (User user : mapper.selectList(lambdaQueryWrapper)) {
            System.out.println(user);
        }
    }

    @Test
    public void testTableFieldExistFalse() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("age, count(age) as count")
                .groupBy("age");
        List<User> list = mapper.selectList(wrapper);
        list.forEach(System.out::println);
        list.forEach(x -> {
            Assert.assertNull(x.getId());
            Assert.assertNotNull(x.getAge());
            Assert.assertNotNull(x.getCount());
        });
        mapper.insert(
                new User().setId(1)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(3));
        User miemie = mapper.selectById(10088L);
        Assert.assertNotNull(miemie);

    }
}
