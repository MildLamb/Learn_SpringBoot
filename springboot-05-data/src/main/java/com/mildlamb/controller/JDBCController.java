package com.mildlamb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JDBCController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Map伪造数据
    //查询所有用户信息
    @GetMapping("/users")
    public List<Map<String,Object>> getUsers(){
        String sql = "select * from mybatis.user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }


    @GetMapping("/addUser")
    public String addUser(){
        String sql = "insert into mybatis.user(id,name,pwd) values(6,'Test','123456')";
        jdbcTemplate.execute(sql);
        return "true";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Integer uid){
        String sql = "update mybatis.user set name = ?,pwd = ? where id = " + uid;
        jdbcTemplate.update(sql,"Demo",21212112);
        return "OK";
    }


    @GetMapping("/delUser/{id}")
    public String delUser(@PathVariable("id") Integer uid){
        String sql = "delete from mybatis.user where id = " + uid;
        jdbcTemplate.execute(sql);
        return "true";
    }
}
