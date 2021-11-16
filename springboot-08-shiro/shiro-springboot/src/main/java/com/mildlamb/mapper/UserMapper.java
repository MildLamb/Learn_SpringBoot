package com.mildlamb.mapper;

import com.mildlamb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    User selectUserByName(@Param("username") String name);
}
