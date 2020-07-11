package com.pratice.jdbc;

import com.test.dao.ReflectionUtils;
import org.apache.commons.beanutils.BeanUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-02-10:13
 */
public class DAO {

    public void update(String sql ,Object ... args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = JDBCTools.getConnection();
            preparedStatement = connection.prepareStatement(sql);


            for (int i=0;i< args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,preparedStatement,connection);
        }
    }
    //查询一条记录，返回的对象
    public <T> T get(Class<T> clazz,String sql ,Object ...args){

        T entity = null;

        Connection connection = null;

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            //1.获取Connection
            connection = JDBCTools.getConnection();
            //2. 获取PreparedStatement

            preparedStatement = connection.prepareStatement(sql);

            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            //4.进行查询，得到结果集
            resultSet = preparedStatement.executeQuery();

            //5.若Resultset有记录的话准备一个Map<String ,Objecet>存放别名和列的值
            if (resultSet.next()) {
                Map<String, Object> values = new HashMap<String, Object>();

                //6.得到ResultSetMetaData对象
                ResultSetMetaData rsmd = resultSet.getMetaData();

                //7.处理ResultSet ，将指针向下移动一个单位

                //8.由ResultSetMetaData 对象结果集有多少列
                int columnCount = rsmd.getColumnCount();

                //9.由ResultSetMetadata 得到每一列的别名，由ResultSet得到具体的每一列的值
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);

                    //10. 填充MAp对象
                    values.put(columnLabel, columnValue);
                }

                //11.用反射键Class 对应的对象
                entity= clazz.newInstance();
                //12.遍历Map对象，用反射填充对象的属性值，更名为MAp的
                // key，属性的MAp的values
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                        String propertyName = entry.getKey();
                        Object value = entry.getValue();

                    ReflectionUtils.setFieldValue(entity,propertyName,value);
                    BeanUtils.setProperty(entity,propertyName,value);
                }
            }
            System.out.println(entity);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,preparedStatement,connection);
        }
        return entity;
    }

    //查询多条记录，返回对应的对象的结合
    public <T> List<T> getForList(Class<T> clazz , String sql , Object ... args){

        // 5. 准备一个LISt<MAp><String,Object >
        //7. 处理Resultset，使用while循环
        //11. 把填充好的map对象放入5准备的List中
        //12 用反射创建Class 对应的对象
        return null;
    }

    //返回某条记录的某一个字段的值或一个统计的值（一共有多少条记录等）
    public <E> E getForValue(String sql, Object... args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            //1.得到结果集：该结果集应该只有一行，且只有一列
            connection = JDBCTools.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return (E) resultSet.getObject(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,preparedStatement,connection);
        }
        //2.取得结果集
        return null;
    }

}
