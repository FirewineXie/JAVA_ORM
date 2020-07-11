package com.test.dao;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-11-15:40
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 访问数据的Dao接口
 * 里面定义好访问数据库的各种方法
 * @parm T : DAO 处理的实体类的类型
 */
public interface DAO<T> {

    /**
     * 批量处理的方法
     * @param connection
     * @param sql
     * @param args  填充占位符的Object 【】 类型的可变参数
     */
    void batch(Connection connection,String sql,Object ... args);
    /**
     * 返回具体的一个值，例如总人数，平均工资，，某个人的email
     * @param connection
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    <E> E getForValue(Connection connection,String sql,Object ... args);
    /**
     * 返回一个T 的集合
     * @param connection
     * @param sql
     * @param args
     * @return
     */
    List<T> getForList(Connection connection,String sql,Object ... args);

    /**
     * 返回一个T对象
     * @param connection
     * @param sql
     * @param args
     * @return
     */
    T get(Connection connection,String sql,Object ... args) throws SQLException;

    /**
     * insert，update，delete
     *
     * @param connection
     * @param sql
     * @param args
     */
    void update(Connection connection,String sql,Object ... args);

}
