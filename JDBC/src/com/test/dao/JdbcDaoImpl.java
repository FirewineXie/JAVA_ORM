package com.test.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-11-15:49
 */

/**
 * 使用QueryRunner 提供其具体的实现
 * @param <T> 子类需传入的泛型类型
 */
public class JdbcDaoImpl<T> implements DAO {

    private QueryRunner queryRunner = null;
    private Class<T> type;
    public JdbcDaoImpl(){
        queryRunner = new QueryRunner();
        type = ReflectionUtils.getSuperGenericType(getClass());
    }


    @Override
    public void batch(Connection connection, String sql, Object... args) {

    }

    @Override
    public List getForList(Connection connection, String sql, Object... args) {
        return null;
    }

    @Override
    public Object get(Connection connection, String sql, Object... args) throws SQLException {


        return queryRunner.query(connection,sql,
                new BeanHandler<>(type),args);
    }


    @Override
    public void update(Connection connection, String sql, Object... args) {

    }

    @Override
    public Object getForValue(Connection connection, String sql, Object... args) {
        return null;
    }
}
