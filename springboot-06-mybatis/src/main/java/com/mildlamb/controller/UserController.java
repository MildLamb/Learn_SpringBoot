package com.mildlamb.controller;

import com.mildlamb.mapper.UserMapper;
import com.mildlamb.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    //查询所有用户
    @GetMapping("/users")
    public List<User> selectUsers(){
        return userMapper.selectUsers();
    }

    //通过id查询用户
    @GetMapping("/user/{uid}")
    public User selectUserById(@PathVariable("uid") Integer id){
        return userMapper.selectUserById(id);
    }

    //添加用户
    @GetMapping("/addUser")
    int addUser(){
        User user = new User(7,"root","W2kindred");
        return userMapper.addUser(user);
    }

    //修改用户
//    int updateUser(User user);

    //删除用户
    @GetMapping("/delUser/{uid}")
    public int delUser(@PathVariable("uid") Integer id){
        return userMapper.delUser(id);
    }
}
