package com.mildlamb.dao;

import com.mildlamb.pojo.Department;
import com.mildlamb.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//员工dao
@Repository
public class EmployeeDao {

    @Autowired
    private Map<Integer,Employee> employeeMap;

    @Autowired
    private static DepartmentDao departmentDao;

    public EmployeeDao() {
    }

    public EmployeeDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    //获得所有员工
    public Collection<Employee> getEmployee(){
        return employeeMap.values();
    }

    //获得对应id员工
    public Employee getEmployeeById(Integer eid){
        return employeeMap.get(eid);
    }

    //添加一名员工
    public void addEmployee(Employee employee){
//        employee.setDepartment(departmentDao.getDepartmentById(employee.getDepartment().getId()));
        employeeMap.put(employee.getId(),employee);
    }

    //修改一名员工
    public void updateEmployee(Integer eid,Employee employee){
        employeeMap.put(eid,employee);
    }

    //删除员工
    public void delEmployee(Integer eid){
        employeeMap.remove(eid);
    }
}
