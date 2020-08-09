package com.mybatis.dao;

import com.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @version : 1.0
 * @auther : Firewine

 * @Program Name: <br>
 * @Create : 2018-11-18-16:06
 */
public interface EmployeeMapper {

    //多条记录封装一个map,Map<Integer , Employee>:键是这条记录的主键，值是记录封装后的java对象
    //告诉mybatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("lastName")
    public Map<String,Employee> getEmpByLastNameLikeReturnMap(String lastName);


    //返回一条记录的map，key就是列名，值就是对应的值
    @MapKey("id")
    public Map<String ,Object> getEmpByIdReturnMap(Integer id);

    public List<Employee> getEmpsByLastNameLike(String lastName);

    public Employee getEmpByMap(Map<String,Object> map);
    /**
     * 可以使用Param来进行命令参数，这样可以在sql的映射文件中，可以使用名字来当参数
     * @param id
     * @param lastName
     * @return
     */
    public Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);

    public Employee getEmpById(Integer id);

    public void addEmp(Employee employee);

    public void updateEmp(Employee employee);

    public void deleteEmpById(Integer id);
}
