# 整合jdbc
- 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```
- 配置数据源
```yml
spring:
  datasource:
    username: root
    password: W2kindred
    url: jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
```
- controller
```java
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
```
- 测试
```java
@SpringBootTest
class Springboot05DataApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        //查看对应的数据源
        System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        String sql = "select * from user";
        System.out.println(connection);
        connection.close();
    }

}
```
