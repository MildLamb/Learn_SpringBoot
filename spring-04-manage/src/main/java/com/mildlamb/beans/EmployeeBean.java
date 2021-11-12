package com.mildlamb.beans;

import com.mildlamb.pojo.Department;
import com.mildlamb.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class EmployeeBean {

    @Autowired
    private DepartmentBean departmentBean;

    @Bean
    public Map<Integer, Employee> getEmployees(){
        Map<Integer, Employee> employees = new HashMap<Integer, Employee>();

        employees.put(1,new Employee(1,"kindred","1902524390@qq.com",false,new Date(),departmentBean.getDepartments().get(101)));
        employees.put(2,new Employee(2,"gnar","1216982545@qq.com",true,new Date(),departmentBean.getDepartments().get(102)));
        employees.put(3,new Employee(3,"neeko","1636757440@qq.com",false,new Date(),departmentBean.getDepartments().get(103)));

        return employees;
    }
}
