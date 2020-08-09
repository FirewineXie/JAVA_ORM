package com.test.page;

import com.page.bean.Role;
import com.page.dao.RoleMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @version : 1.0
 * @auther : Firewine
 * @email : 1451661318@qq.com
 * @ProgramName: <br>
 * @Create : 2018-12-16-20:08
 * @Description : <br/>
 */
public class Test {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return  new SqlSessionFactoryBuilder().build(inputStream);
    }


    public void select() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession sqlSession = sqlSessionFactory.openSession();


        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<Role> list = roleMapper.selectAll(new RowBounds(5,10));
            for (Role role : list){
                System.out.println("role ::->>>" + role);
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            sqlSession.close();
        }
    }
}
