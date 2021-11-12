package com.mildlamb.beans;

import com.mildlamb.pojo.Department;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DepartmentBean {
    @Bean
    public Map<Integer,Department> getDepartments(){
        Map<Integer,Department> departments = new HashMap<Integer, Department>();  //创建一个伪部门表
        departments.put(101,new Department(101,"教学部"));
        departments.put(102,new Department(102,"市场部"));
        departments.put(103,new Department(103,"财务部"));
        departments.put(104,new Department(104,"运营部"));
        departments.put(105,new Department(105,"后勤部"));
        return departments;
    }
}
