package com.mildlamb.mapper;

import com.mildlamb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper注解表示了这是一个mybatis的mapper类
@Mapper
@Repository
public interface UserMapper {
    //查询所有用户
    List<User> selectUsers();

    //通过id查询用户
    User selectUserById(@Param("uid") Integer id);

    //添加用户
    int addUser(User user);

    //修改用户
    int updateUser(User user);

    //删除用户
    int delUser(@Param("uid") Integer id);


}
