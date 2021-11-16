package com.mildlamb.service;

import com.mildlamb.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    User selectUserByName(@Param("username") String name);
}
