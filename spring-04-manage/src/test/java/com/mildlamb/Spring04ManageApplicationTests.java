package com.mildlamb;

import com.mildlamb.beans.DepartmentBean;
import com.mildlamb.beans.EmployeeBean;
import com.mildlamb.dao.DepartmentDao;
import com.mildlamb.pojo.Department;
import com.mildlamb.pojo.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class Spring04ManageApplicationTests {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private EmployeeBean employeeBean;
    @Autowired
    private DepartmentBean departmentBean;



    @Test
    void contextLoads() {
        Map<Integer, Employee> employees = employeeBean.getEmployees();
        Set<Map.Entry<Integer, Employee>> entries = employees.entrySet();
        for (Map.Entry<Integer, Employee> entry : entries) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println("===========");
        }
    }

    @Test
    void contextLoads2() {
    }

}
