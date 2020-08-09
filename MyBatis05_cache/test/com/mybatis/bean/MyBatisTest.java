package com.mybatis.bean;

import com.mybatis.dao.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @version : 1.0
 * @auther : Firewine

 * @Program Name: <br>
 * @Create : 2018-11-18-15:44
 * <p>
 * 1. 接口式编程
 * 原生 Dao ---->   DaoImpi
 * mybatis    Mapper ---->  xxMapper.xml
 * <p>
 * 2. SqlSession 代表和数据库的一次会话，用完关闭
 * 3. SQLSession和connection 一样都是非线程安全，每次使用都获取新的对象。
 * （不可以写成员变量 private SQLSession SQLSession ）
 * 4. mapper接口没有实现类，但是MyBatis会为这个接口生成一个代理对象
 * (将接口和xml进行绑定)
 * SQLSession。getMapper （EmployeeMapper.class）
 * <p>
 * 5. 两个重要的配置文件
 * MyBatis的全局配置文件，包括数据库连接池信息，事务管理器信息等，系统运行环境等
 * sql映射文件，保存了sql的映射信息，这样可以将sql抽取出来
 */
public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }


    /**
     * 两级缓存：
     * 一级缓存 ：（本地缓存）:SqlSession级别的缓存，一级缓存是一直开启的
     *          与数据库同一次会话期间查询到的数据会放在本地缓存中
     *          以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库
     *
     *          一级缓存失效情况 ： 效果就是，还需要再向数据库发出查询
     *          1.SqlSession 不同
     *          2.SQLSession 相同，查询条件不同（当前一级缓存没有这个数据）
     *          3.SQLSession 相同，两次查询之间执行了增删改操作。（这次增删改可能会改变查找的数据）
     *          4. SQLSession 相同，手动清除了一级缓存(缓存清空)
     * 二级缓存： （全局缓存）： 基于namespace级别的缓存：一个namespace对应一个二级缓存
     *          工作机制：
     *              1. 一个会话 ：查询一条数据，这个数据就会被放在当前会话的一级缓存那种；
     *              2. 如果会话关闭，一级缓存中的数据会被保存到二级缓存中，新的会话查询信息，就可以参照二级缓存
     *              3. Sqlsession === EmployeeMapper ==> Employee
     *                                  DepartmentMapper ===> Department
     *                     不同的namespace查出的数据就会放在自己对应的namespace中(map)
     *                     效果：数据会从二级缓存中获取
     *                              查出的数据都会被默认先放在一级缓存中
     *                              只用会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中去
     *              使用 ：
     *                  1）开启全局二级缓存配置  ； <setting name="cacheEnabled" value="true"/>
     *                  2）去mapper.xml 中配置使用二级缓存
     *
     *和缓存有关的设置/属性：
     * 			1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
     * 			2）、每个select标签都有useCache="true"：
     * 					false：不使用缓存（一级缓存依然使用，二级缓存不使用）
     * 			3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
     * 					增删改执行完成后就会清楚缓存；
     * 					测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
     * 					查询标签：flushCache="false"：
     * 						如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
     * 			4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
     * 			5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
     * 								STATEMENT：可以禁用一级缓存；
     *
     *第三方缓存整合：
     *		1）、导入第三方缓存包即可；
     *		2）、导入与第三方缓存整合的适配包；官方有；
     *		3）、mapper.xml中使用自定义缓存
     *		<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
     *
     * @throws IOException
     */
     @Test
    public void testSecondLevelCache02() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        SqlSession openSession2 = sqlSessionFactory.openSession();
        try{
            //1、
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
            DepartmentMapper mapper2 = openSession2.getMapper(DepartmentMapper.class);

            Department deptById = mapper.getDeptById(1);
            System.out.println(deptById);
            openSession.close();



            Department deptById2 = mapper2.getDeptById(1);
            System.out.println(deptById2);
            openSession2.close();
            //第二次查询是从二级缓存中拿到的数据，并没有发送新的sql

        }finally{

        }
    }
    @Test
    public void testSecondLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        SqlSession openSession2 = sqlSessionFactory.openSession();
        try {
            //1、
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
            DepartmentMapper mapper2 = openSession2.getMapper(DepartmentMapper.class);

            Department department1 = mapper.getDeptById(1);
            System.out.println(department1);
            openSession.close();

            //第二次查询是从二级缓存中拿到的数据，并没有发送新的sql
            //mapper2.addEmp(new Employee(null, "aaa", "nnn", "0"));
            Department department2 = mapper2.getDeptById(1);
            System.out.println(department2);
            openSession2.close();

        } finally {

        }
    }
    @Test
    public void testSecondLeveCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        try {
            //1.
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            EmployeeMapper employeeMapper2 = sqlSession2.getMapper(EmployeeMapper.class);

            Employee employee1 = employeeMapper.getEmpById(1);
            System.out.println(employee1);
            sqlSession.close();
            //第二次查询是从二级缓存中拿到的数据，并没有发送新的sql
            Employee employee2 = employeeMapper2.getEmpById(1);
            System.out.println(employee2);
            sqlSession2.close();
        }finally {

        }
    }
    @Test
    public void TestFirstLeveCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{

            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee emp01 = mapper.getEmpById(1);
            System.out.println(emp01);

            //xxx
            Employee emp02 = mapper.getEmpById(1);
            System.out.println(emp02);
        }finally {

            sqlSession.close();
        }
    }
}
