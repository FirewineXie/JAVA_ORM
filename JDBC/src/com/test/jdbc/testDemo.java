package com.test.jdbc;


import com.DBUtils.db.Customer;
import com.test.dao.ReflectionUtils;
import org.junit.Test;



import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-09-30-15:26
 */
public class testDemo {
    @Test
    public void testResultSetMetaData(){
        Connection connection = null;
        PreparedStatement preparedStatement =null;

        ResultSet resultSet =null;

        try{
            String sql = "select flow_id flowId ,type,id_card idCard,exam_card examCard ,student_Name studentName,"+
                             "location,grade from examstudent where flow_id =?";
            connection = testJdbc.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,5);

            resultSet = preparedStatement.executeQuery();

            Map<String,Object> values =
                    new HashMap<String,Object>();
            //1.得到Resultset Setmetdat 度下个
            ResultSetMetaData rsmd = resultSet.getMetaData();


            while (resultSet.next()){

                //2.打印每一列的列名
                for (int i=0;i < rsmd.getColumnCount();i++){
                    String columnLabel = rsmd.getColumnLabel(i + 1);
//                  System.out.println(columnLabel);
                    Object columnValue = resultSet.getObject(columnLabel);

                    values.put(columnLabel,columnValue);
                }
            }
//            System.out.println(values);
            Class clazz  =Student.class;

            Object object = clazz.newInstance();

            for (Map.Entry<String, Object> entry: values.entrySet()){
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                System.out.println(fieldName + ":" + fieldValue);
                ReflectionUtils.setFieldValue(object,fieldName,fieldValue);
            }
            System.out.println(values);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            testJdbc.closeConnection(resultSet,preparedStatement,connection);
        }
//        System.out.println(stu);


    }

    @Test
    public void testGet(){
        String sql = "select id ,name,email ,birth "+
                    "from customers where id =?";
        Customer customer = get(Customer.class,sql,5);

//        System.out.println(customer);

        sql = "select flow_id ,type,id_card,exam_card,student_name,"+
                "location,grade from examstudent where flow_id =?";

        Student stu = get(Student.class,sql,3);
        System.out.println(stu);
    }

    /**
     * 通用的查询方法，可以根据传入的sql class对象返回sql 对应的记录的对象
     * @param clazz  描述对象的类型
     * @param sql sql语句，
     * @param args 填充占位符的可变餐厨
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> clazz,String sql,Object ...args){
        T entity = null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;

        ResultSet resultSet =null;

        try{
            //1. 得到ResultSet 对象

            connection = testJdbc.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            //2. 得到ResultsetMetadata对象
                ResultSetMetaData rsmd = resultSet.getMetaData();
            //3.创建一个Map<String oBject >对象

            Map<String,Object> values = new HashMap<>();
            //4.处理结果集，利用ResultsetSetMeatdata对象 填充3 对象的map对象
            if (resultSet.next()){
                for (int i=0;i < rsmd.getColumnCount();i++){
                    String columnLabel = rsmd.getColumnLabel(i +1);
                    Object columnValue = resultSet.getObject(i+1);

                    values.put(columnLabel,columnValue);
                }
            }
            //5. 若map 不是空，利用反射创见clazz 的对象

            if (values.size() > 0){
                entity = clazz.newInstance();
            }
            //6.遍历map对昂，利用反射为class 对象的对应属性赋值
            for (Map.Entry<String,Object> entry:values.entrySet()){
                String fiedName = entry.getKey();
                Object value = entry.getValue();
                ReflectionUtils.setFieldValue(entry,fiedName,value);
            }

            if (resultSet.next()){
                //通过反射创建对象
                entity = clazz.newInstance();

                //通过解析sql 语句来判断到底选择了那些列，以及需要为entity对象
                //那些属性赋值


            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            testJdbc.closeConnection(resultSet,preparedStatement,connection);
        }
//        System.out.println(stu);


        return entity;
    }
    public  Customer getCustomer(String sql,Object ... args){
        Customer customer =null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;

        ResultSet resultSet =null;

        try{
            connection = testJdbc.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
//                stu = new Student();
//                stu.setFlaowid(resultSet.getInt(1));
//                stu.setType(resultSet.getInt(2));
//                stu.setIdCard(resultSet.getString(3));
//                stu.setExamCard(resultSet.getString(4));
//                stu.setStudentName(resultSet.getString(5));
//                stu.setLocation(resultSet.getString(6));
//                stu.setGrade(resultSet.getInt(7));
                customer = new Customer();
                customer.setId(resultSet.getInt(1));
                customer.setName(resultSet.getString(2));
                customer.setEmail(resultSet.getString(3));
                customer.setBirth(resultSet.getDate(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            testJdbc.closeConnection(resultSet,preparedStatement,connection);
        }
//        System.out.println(stu);
        return customer;
    }
    public  Student getStudent(String sql,Object ... args){
        Student stu = null;
        Connection connection = null;
        PreparedStatement preparedStatement =null;

        ResultSet resultSet =null;

        try{
            connection = testJdbc.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                stu = new Student();
                stu.setFlowID(resultSet.getInt(1));
                stu.setType(resultSet.getInt(2));
                stu.setIdCard(resultSet.getString(3));
                stu.setExamCard(resultSet.getString(4));
                stu.setStudentName(resultSet.getString(5));
                stu.setLocation(resultSet.getString(6));
                stu.setGrade(resultSet.getInt(7));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            testJdbc.closeConnection(resultSet,preparedStatement,connection);
        }
        System.out.println(stu);
        return stu;
    }

    /**
     * 使用PreparedStatement 有效防止sql注入
     */
    @Test
    public void testSQLInjection2(){
        String  username = "a' OR PASS WORD =";
        String password = "OR '1'='1";
        String sql = "select * from users where username = ?"
                +" AND "+
                "password = ?";



        Connection connection =null;
        PreparedStatement preparedStatement =null;

        ResultSet resultSet =null;

        try{
            connection = testJdbc.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                System.out.println("登录成功");
            }else{
                System.out.println("用户名和密码不正确");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            {
                testJdbc.closeConnection(resultSet,preparedStatement,connection);
            }
        }

    }
    /**
     * sql注入
     */
    @Test
    public void testSQLInjection(){
        String  username = "a' OR PASSWORD =";
        String password = "OR '1'='1";
        String sql = "select * from users where username = '"+username
                +"' AND "+
                "password ='"+password+"' ";

        System.out.println(sql);

        Connection connection =null;
        Statement statement = null;

        ResultSet resultSet =null;

        try{
            connection = testJdbc.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                System.out.println("登录成功");
            }else{
                System.out.println("用户名和密码不正确");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            {
                testJdbc.closeConnection(resultSet,statement,connection);
            }
        }
    }
    @Test
    public void testPreParedStatemnet(){
        //创建PreparedStatement
        //创建占位符，
        Connection connection = null;
        PreparedStatement preparedStatement =null;

        try {
            connection = testJdbc.getConnection();
            String sql = "insert into customers (name,email,birth)" +
                    "values(?,?,?)";

            preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setString(1,"qwer");
            preparedStatement.setString(2,"13@163.com");
            preparedStatement.setDate(3,new Date(new Date(new java.util.Date().getTime()).getTime()));

            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            testJdbc.closeConnection(null,preparedStatement,connection);
        }
    }
    @Test
    public void testAddNewStudent(){
        Student student = getStudentFromConsole();
        addNewStudent2(student);
    }
     public static void main(String[] args){
         testDemo d = new testDemo();
         Student student = d.getStudentFromConsole();
         d.addNewStudent2(student);
//
//        //1.得到查询的类型
//        int searchtype = d.getSearchTyppeFromConsole();
//        //2.具体查询学生信息
//        Student student = d.searchStudent(searchtype);
//        //3.打印学生信息
//        d.printStudent(student);
////        testDemo d = new testDemo();
////        Student student = d.getStudentFromConsole();
////        d.addNewStudent(student);
//
    }
    @Test
    public void testGetStudent(){
        //1.得到查询的类型
        int searchtype = getSearchTyppeFromConsole();
        //2.具体查询学生信息
        Student student = searchStudent(searchtype);
        //3.打印学生信息
        printStudent(student);
    }

    /**
     * 打印学生信息：若学生信息存在则打印，不存在，提示重新输入
     * @param student
     */
    private void printStudent(Student student) {
        if(student != null){
            System.out.println(student);
        }else {
            System.out.println("查无此人");
        }
    }

    /**
     * 具体查询学生信息的，返回一个student 对象，若不存在，则返回null
     * @param searchtype 1 或者2
     * @return
     */
    private Student searchStudent(int searchtype) {

        String sql = "SELECT * "+
                "from examstudent "+
                "where ";
        //1.根据输入的searchTyep ， 提示用户输入信息
        //1.1 若searchType 为1  ， 提示输入身份证号，为2 输入准考证号
        Scanner scanner = new Scanner(System.in);
        if (searchtype == 2){
            System.out.println("请输入身份证号：");
            String examCard = scanner.next();
            sql = sql + "examcard = '"+examCard+"'";
            System.out.println(sql);
        }else{
            System.out.println("请输入准考证号");
            String examCard = scanner.next();
            sql = sql + "idcard = '"+examCard +"'";
            System.out.println(sql);
        }
        //2.根据searchType 确定sql

        //3.查询
        Student student = getStudent(sql);
        //4.存在   ， 查询结果封装student对象
        return student;
    }

    /**
     * 根据传入的sql 返回student对象
     * @param sql
     * @return
     */
    private Student getStudent(String sql) {
        Student stu = null;
        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet =null;

        try{
            connection = testJdbc.getConnection();
            statement =connection.createStatement();
            resultSet  =statement.executeQuery(sql);

            if (resultSet.next()){
                stu = new Student(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            testJdbc.closeConnection(resultSet,statement,connection);
        }
        System.out.println(stu);
        return stu;
    }

    /**
     * 从控制台读入一个整数，确定要查询的类型
     * @return 1.身份证查询，准考证查询，，其他无效
     */
    private int getSearchTyppeFromConsole() {


        System.out.println("请输入查询类型：1.身份证查询，2.准考证查询");
        Scanner scanner = new Scanner(System.in);

        int type = scanner.nextInt();
        if (type != 1 && type != 2){
            System.out.println("输入有误，请重新输入");
            throw new RuntimeException();//错误直接结束掉
        }
        return type;
    }
//    @Test
//    //junit 只支持自动化测试，不持手动输入，容易造成线程阻塞，
//    public void  testAddNewStudent(){
//        Student student = getStudentFromConsole();
//        addNewStudent(student);
//    }



    private Student getStudentFromConsole() {

        Scanner scanner = new Scanner(System.in);

        Student student = new Student();

        System.out.println("");
        student.setFlowID(scanner.nextInt());
        student.setType(scanner.nextInt());
        student.setIdCard(scanner.next());
        student.setExamCard(scanner.next());
        student.setStudentName(scanner.next());
        student.setLocation(scanner.next());
        student.setGrade(scanner.nextInt());

        return student;
    }
    public void addNewStudent2(Student student){
        String sql = "insert into examstudent (flowid,type,idcard,examcard,studentname,location,grade)"+
                "values(?,?,?,?,?,?,?)";

        testJdbc.update(sql,student.getFlowID(),
                student.getType(),
                student.getIdCard(),
                student.getExamCard(),
                student.getStudentName(),
                student.getLocation(),
                student.getGrade());
    }
    public void addNewStudent(Student student){
        //1.准备一条sql语句
        String sql = "INSERT INTO examstudent VALUES ("+
                    "\""+student.getFlowID()+"\","+
                    "\""+student.getType()+"\","+
                    "\""+student.getIdCard()+"\","+
                    "\""+student.getExamCard()+"\","+
                    "\""+student.getStudentName()+"\","+
                    "\""+student.getLocation()+"\","+
                    "\""+student.getGrade()+"\")";

        //2.调用JDBCTools的update 方法执行插入操作
        testJdbc.update(sql);
    }
}
