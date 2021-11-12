package com.mildlamb.controller;

import com.mildlamb.beans.EmployeeBean;
import com.mildlamb.dao.DepartmentDao;
import com.mildlamb.dao.EmployeeDao;
import com.mildlamb.pojo.Department;
import com.mildlamb.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping("/emp/getEmp")
    public String getEmployees(Model model){
        Collection<Employee> employees = employeeDao.getEmployee();
        model.addAttribute("emps",employees);
        return "emp/list";
    }

    @GetMapping("/emp/addEmp")
    public String toAddEmp(Model model){
        //查出所有部门的信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emp/addEmp";
    }

    @PostMapping("/emp/addEmp")
    public String addEmp(Employee employee, @RequestParam("departmentId") Integer did){
        employee.setDepartment(departmentDao.getDepartmentById(did));
        System.out.println("add ==> " + employee);
        //添加员工
        employeeDao.addEmployee(employee);
        return "redirect:/emp/getEmp";
    }

    //去员工的修改页面  回显所选员工信息
    @GetMapping("/emp/updateEmp/{eid}")
    public String toUpdateEmp(@PathVariable("eid") Integer id,Model model){
        //查出所选员工的信息
        Employee employee = employeeDao.getEmployeeById(id);
        //查出部门的信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("employee",employee);
        model.addAttribute("departments",departments);
        return "emp/updateEmp";
    }

    //修改员工信息
    @PostMapping("/emp/updateEmp/{eid}")
    public String updateEmp(@PathVariable("eid") Integer id,Employee employee,@RequestParam("departmentId") Integer did){
        System.out.println("id ==>" + id);
        employee.setDepartment(departmentDao.getDepartmentById(did));
        System.out.println("employee ==> " + employee);
        employeeDao.updateEmployee(id,employee);
        return "redirect:/emp/getEmp";
    }

    @GetMapping("/emp/delEmp/{eid}")
    public String delEmp(@PathVariable("eid") Integer id){
        employeeDao.delEmployee(id);
        return "redirect:/emp/getEmp";
    }
}
