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
 * @email : 1451661318@qq.com
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


    @Test
    public void testInnerParam() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee2 = new Employee();
            employee2.setLastName("%e%");
            List<Employee> list = mapper.getEmpsTestInnerParameter(employee2);
            for (Employee employee : list) {
                System.out.println(employee);
            }
        }finally{
            openSession.close();
        }
    }


    @Test
    public void testBatchSave() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = new ArrayList<>();
            emps.add(new Employee(null, "smith0x1", "smith0x1@atguigu.com", "1",new Department(1)));
            emps.add(new Employee(null, "allen0x1", "allen0x1@atguigu.com", "0",new Department(1)));
            mapper.addEmps(emps);
            openSession.commit();
        }finally{
            openSession.close();
        }
    }


    @Test
    public void testDynamicSql() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();


        try{
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
//            Employee employee = new Employee(null,null,null,null);
//            List<Employee> emps = mapper.getEmpsByConditionIf(employee);
//
//            for (Employee  emp : emps){
//                System.out.println(emp);
//            }

            //查询的时候如果某些条件没带可能sql拼装会有问题
            //1、给where后面加上1=1，以后的条件都and xxx.
            //2、mybatis使用where标签来将所有的查询条件包括在内。mybatis就会将where标签中拼装的sql，多出来的and或者or去掉
                     //where只会去掉第一个多出来的and或者or。

//            //测试Trim
//			List<Employee> emps2 = mapper.getEmpsByConditionTrim(employee);
//			for (Employee emp : emps2) {
//				System.out.println(emp);
//			}

//            //测试choose
//            List<Employee> list = mapper.getEmpsByConditionChoose(employee);
//            for (Employee emp : list){
//                System.out.println(emp);
//            }


            //测试set标签
//            Employee employee = new Employee(1,"Admin",null,null);
//
//            mapper.updateEmp(employee);
//            sqlSession.commit();

            List<Employee> list = mapper.getEmpsByConditionForeach(Arrays.asList(1,2));
            for (Employee emp : list){
                System.out.println(emp);
            }

        }finally {
            sqlSession.close();
        }
    }
    /**
     * 1.根据xml配置文件(全局配置文件)创建一个SQLSessionFactory对象
     * 有数据源一些运行环境信息
     * 2. sql映射文件，配合了每一个sql，以及sql的封装规则等
     * 3. 将sql映射文件注册在全局配置文件中
     * 4. 写代码:
     * 1. 根据全局配置文件得到SQLSessionFactory
     * 2. 使用sqlSession工厂，获取到SQLSession对象使用来执行增删改查，
     * 一个SQLSession就是代表和数据库的一次会话，用完关闭
     * 3. 使用sql的唯一标识来告诉MyBatis执行哪一个sql，sql都是保存在sql映射文件中的
     *
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

        //2. 获取SQLSession实例 ， 能直接执行已经映射的sql语句
        //第一个参数是sql的唯一标识
        //第二个参数是执行sql要用的参数
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //namespace 加 id  更加能区别不一样的id
        try {
            Employee employee = sqlSession.selectOne("org.mybatis.example.BlogMapper.selectBlog", 1);

            System.out.println(employee.toString());
        } finally {
            sqlSession.close();
        }

    }

    @Test
    public void test01() throws IOException {
        //1， 获取SQLSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2. 获取SQLSession对象
        SqlSession openSession = sqlSessionFactory.openSession();


        try {
            //3. 获取接口的实现类对象
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            //4. 会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperAnnotation employeeMapperAnnotation = sqlSession.getMapper(EmployeeMapperAnnotation.class);

            Employee employee = employeeMapperAnnotation.getEmpById(1);

            System.out.println(employee);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 测试增删改查
     * 1. mybatis 容许增删改直接定义以下类型返回值
     *          Integer ，Long， Boolean ，void
     * 2.我们需要手动提交上去
     *  SQLSessionFactory . openSession(true) 就可以自动提交了
     * @throws IOException
     */
    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
//       1. 获取的SQLSession不会自动提交数据
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//            添加
//            Employee employee = new Employee(null, "jerry4","32@atm.com", "1");
//            mapper.addEmp(employee);
//            System.out.println(employee.getId());
//            修改
//            Employee employee = new Employee(1,"jerry","jerry@232.com","0");
//
//            mapper.updateEmp(employee);

//            删除
//            mapper.deleteEmpById(2);

            //2.手动提交
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }
    @Test
    public void test04() throws IOException {
        //1， 获取SQLSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2. 获取SQLSession对象
        SqlSession openSession = sqlSessionFactory.openSession();


        try {
            //3. 获取接口的实现类对象
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

//            Employee employee = mapper.getEmpByIdAndLastName(24,"tom");
//            Map<String,Object> map = new HashMap<>();
//            map.put("id",24);
//            map.put("lastName","Tom");
//            map.put("tableName","tb1_employee");
//            Employee employee = mapper.getEmpByMap(map);
//            List<Employee> like = mapper.getEmpsByLastNameLike("%e%");
//            for (Employee employee :like){
////                System.out.println(employee);
//            }
////            System.out.println(employee);
//
//            Map<String,Object> map = mapper.getEmpByIdReturnMap(20);
//            System.out.println(map);
            Map<String,Employee> map = mapper.getEmpByLastNameLikeReturnMap("%e%");
            System.out.println(map);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test05() throws IOException {
        //1， 获取SQLSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2. 获取SQLSession对象
        SqlSession openSession = sqlSessionFactory.openSession();


        try {
            //3. 获取接口的实现类对象
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
//
//            Employee employee = mapper.getEmpById(23);
//            System.out.println(employee);
//            Employee employeeDept = mapper.getEmpAndDept(1);
//            System.out.println(employeeDept);
//            System.out.println(employeeDept.getDept());
            Employee employee = mapper.getEmpByIdStep(1);
//            System.out.println(employee);
            System.out.println(employee.getDept());
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test06() throws IOException {
        //1， 获取SQLSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2. 获取SQLSession对象
        SqlSession openSession = sqlSessionFactory.openSession();


        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
//
//            Department department = mapper.getDeptByIdPlus(1);
//
//            System.out.println(department);
//            System.out.println(department.getEmps());
            Department departmentStep = mapper.getDeptByIdStep(3);
            System.out.println(departmentStep);
            System.out.println(departmentStep.getEmps());
        } finally {
            openSession.close();
        }
    }
}
