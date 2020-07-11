package com.pratice.jdbc;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @version : 1.0
 * @auther : Firewine
 * @Program Name: <br>
 * @Create : 2018-10-02-22:42
 */
public class BeanUtilsTest {
    @Test
    public void testGetProPerty() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object object = new Student();

//        System.out.println(object);

        BeanUtils.setProperty(object,"idCard","211211965009002");
        System.out.println(object);

        Object val = BeanUtils.getProperty(object,"idCard3");
        System.out.println(val);
    }
    @Test
    public void testSetProperty()throws IllegalAccessException, InvocationTargetException {
        Object object = new Student();

//        System.out.println(object);

        BeanUtils.setProperty(object,"idCard","211211965009002");
        System.out.println(object);

    }
}
