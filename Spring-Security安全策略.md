# SpringSecurity(安全)
## Spring Security简介
Spring Security是针对Spring项目的安全框架，也是Spring Boot底层安全模块默认的技术选型，他可以实现强大的Web安全控制，对于安全控制，我们仅需要引入spring-boot-starter-security模块，进行少量的配置，即可实现强大的安全管理!  
**记住几个类**
- WebSecurityConfigurerAdapter : 自定义Security策略
- AuthenticationManagerBuilder : 自定义认证策略
- @EnableWebSecurity : 开启WebSecurity模式

Spring Security的两个主要目标是“认证”和“授权”(访问控制)
- 认证(Authentication)
- 授权(Authorization)

# 使用SpringSecurity
- 导入依赖
```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
- 自定义一个配置类 继承WebSecurityConfigurerAdapter类 标注@EnableWebSecurity注解 重写configure方法
```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
```
- 例子
```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //请求授权规则
        //首页所有人可以访问，但里面的功能页只有对应有权限的人可以访问
        //首页 所有人可以访问
        http.authorizeRequests().antMatchers("/","/index","/index.html").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        //没有权限默认会跳转到security登录页面
        //开启登录页面
        http.formLogin();
    }


    //认证规则
    //新版需要密码加密 否则会报500错误  There is no PasswordEncoder mapped for the id "null"
    //在spring security 5.0+ 新增了很多加密方式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //内存方式的验证
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("kindred").password(new BCryptPasswordEncoder().encode("W2kindred")).roles("vip1","vip2").and()
//                .withUser("gnar").password(new BCryptPasswordEncoder().encode("W2snowgnar")).roles("vip1","vip2","vip3").and()
//                .withUser("neeko").password(new BCryptPasswordEncoder().encode("q1216982545")).roles("vip1");

        //jdbc方式的验证
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from users WHERE username=?")
                .authoritiesByUsernameQuery("select username,authority from authorities where username=?")
                .passwordEncoder(new BCryptPasswordEncoder());

    }
}
```
## 主要说明一下如何使用数据库进行用户验证
- 创建一个默认的schema 可以在 (org/springframework/security/core/userdetails/jdbc/users.ddl)找到
```sql
create table `users`(`username` varchar(50) not null primary key,`password` varchar(500) not null,enabled boolean not null);
create table `authorities` (`username` varchar(50) not null,`authority` varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);
```
- users表 username password enabled 属性说明
  - username：用户名
  - password：密码(新版现在要求要使用一种加密才能使用，不能是明文的密码，否则会报500错误  There is no PasswordEncoder mapped for the id "null")
  - enabled：0，1 表示这个用户是否被启用
- authorities表 username authority 属性说明
  - username：关联users表的外键key
  - authority：角色 注意：一定要ROLE_  开头 否则是不会识别的

- 表中的数据可以手动添加也可以使用UserDetailsManager来管理,只需要在spring中配置bean,会自动向刚刚的表中插入对应的数据
- 注意这里的User类是，import org.springframework.security.core.userdetails.User; 中的
```java
@Configuration
public class UserSetConfig {
    @Bean
    UserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.builder()
                .username("QSJ")
                .password("$2a$10$3GIxvmbs4cITKMbty8cUs.NXQiJitm8iQnjRfjmHI0lmCMpqf3kzq")
                .roles("vip1","vip3")
                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles("USER", "ADMIN")
//                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
//        users.createUser(admin);
        return users;
    }
}
```
## 继续完善
### 我们想要对应的用户登录进来只显示对应权限所拥有的界面
- 导入thymeleaf和spring-security的整合依赖
```xml
<!-- thymeleaf-security整合依赖 -->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
    <version>3.0.4.RELEASE</version>
</dependency>
```
- 前端页面，sec 判断用户有哪些角色(权限)
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:sec=http://www.thymeleaf.org/extras/spring-security>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>首页</title>
    <!--semantic-ui-->
    <link href="https://cdn.bootcss.com/semantic-ui/2.4.1/semantic.min.css" rel="stylesheet">
    <link th:href="@{/asserts/css/qinstyle.css}" rel="stylesheet">
</head>
<body>

<!--主容器-->
<div class="ui container">

    <div class="ui segment" id="index-header-nav" th:fragment="nav-menu">
        <div class="ui secondary menu">
            <a class="item"  th:href="@{/index}">首页</a>

            <!--登录注销-->
            <div class="right menu">
                <!--如果未登录(未验证)  sec:authorize="!isAuthenticated()" -->
                <div sec:authorize="!isAuthenticated()">
                    <a class="item" th:href="@{/toLogin}">
                        <i class="address card icon"></i> 登录
                    </a>
                </div>

                <!-- 如果登录：用户名 和 注销
                    sec:authorize="isAuthenticated()"  -- 已验证
                -->
                <div sec:authorize="isAuthenticated()">
                    <a class="item">
                        用户名:<span sec:authentication="name"></span>  <!--登录security成功后可以用这个获取用户名-->
                        权限(角色):<span sec:authentication="principal.authorities"></span>  <!--登录security成功后可以用这个获取用户权限-->
                    </a>
                </div>
                <!--注销-->
                <div sec:authorize="isAuthenticated()">
                    <a class="item" th:href="@{/logout}">
                        <input type="submit" class="sign-out icon" value="注销">
                    </a>
<!--                    <form class="item" th:action="@{/logout}" th:method="post">-->
<!--                        <input type="submit" class="sign-out icon" value="注销">-->
<!--                    </form>-->
                </div>
            </div>
        </div>
    </div>

    <div class="ui segment" style="text-align: center">
        <h3>Spring Security Study</h3>
    </div>

    <div>
        <br>
        <div class="ui three column stackable grid">
            <!-- 显示的菜单根据用户的权限动态显示 -->
            <div class="column" sec:authorize="hasRole('vip1')">
                <div class="ui raised segment">
                    <div class="ui">
                        <div class="content">
                            <h5 class="content">Level 1</h5>
                            <hr>
                            <div><a th:href="@{/level1/1}"><i class="bullhorn icon"></i> Level-1-1</a></div>
                            <div><a th:href="@{/level1/2}"><i class="bullhorn icon"></i> Level-1-2</a></div>
                            <div><a th:href="@{/level1/3}"><i class="bullhorn icon"></i> Level-1-3</a></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="column" sec:authorize="hasRole('vip2')">
                <div class="ui raised segment">
                    <div class="ui">
                        <div class="content">
                            <h5 class="content">Level 2</h5>
                            <hr>
                            <div><a th:href="@{/level2/1}"><i class="bullhorn icon"></i> Level-2-1</a></div>
                            <div><a th:href="@{/level2/2}"><i class="bullhorn icon"></i> Level-2-2</a></div>
                            <div><a th:href="@{/level2/3}"><i class="bullhorn icon"></i> Level-2-3</a></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="column" sec:authorize="hasRole('vip3')">
                <div class="ui raised segment">
                    <div class="ui">
                        <div class="content">
                            <h5 class="content">Level 3</h5>
                            <hr>
                            <div><a th:href="@{/level3/1}"><i class="bullhorn icon"></i> Level-3-1</a></div>
                            <div><a th:href="@{/level3/2}"><i class="bullhorn icon"></i> Level-3-2</a></div>
                            <div><a th:href="@{/level3/3}"><i class="bullhorn icon"></i> Level-3-3</a></div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>


<script th:src="@{/asserts/js/jquery-3.3.1.min.js}"></script>
<script th:src="@{/asserts/js/semantic.min.js}"></script>

</body>
</html>
```
