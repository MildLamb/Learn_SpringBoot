# 整合Mybatis
- 导入依赖
```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.0</version>
</dependency>
```
- mapper类
```java
//@Mapper注解表示了这是一个mybatis的mapper类  或者在启动类使用@MapperScan
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
```
- mapper.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.mildlamb.mapper.UserMapper">
    <select id="selectUsers" resultType="user">
        select * from mybatis.user;
    </select>

    <select id="selectUserById" resultType="user" parameterType="int">
        select * from mybatis.user
        <where>
            id = #{uid}
        </where>
    </select>

    <insert id="addUser" parameterType="user">
        insert into mybatis.user(id, name, pwd) values(#{id},#{name},#{pwd})
    </insert>

    <update id="updateUser" parameterType="user">
        update mybatis.user set name = #{name},pwd = #{pwd}
        <where>
            id = #{id}
        </where>
    </update>

    <delete id="delUser" parameterType="int">
        delete from mybatis.user
        <where>
            id = #{uid}
        </where>
    </delete>
</mapper>
```
- application.yml
```xml
spring:
  datasource:
    username: root
    password: W2kindred
    url: jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver
# 整合mybatis
mybatis:
  type-aliases-package: com.mildlamb.pojo
  mapper-locations: classpath:mybatis/mapper/*.xml
```
