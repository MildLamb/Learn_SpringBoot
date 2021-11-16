package com.mildlamb.service.impl;

import com.mildlamb.mapper.UserMapper;
import com.mildlamb.pojo.User;
import com.mildlamb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByName(String name) {
        return userMapper.selectUserByName(name);
    }
}
