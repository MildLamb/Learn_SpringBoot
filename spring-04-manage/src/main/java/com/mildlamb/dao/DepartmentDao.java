package com.mildlamb.dao;

import com.mildlamb.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//部门dao
@Repository
public class DepartmentDao {

    @Autowired
    private Map<Integer,Department> departmentMap;

    //获得部门信息
    public Collection<Department> getDepartments(){
        System.out.println(departmentMap.values());
        return departmentMap.values();
    }

    //通过id获取部门信息
    public Department getDepartmentById(Integer did){
        return departmentMap.get(did);
    }
}
