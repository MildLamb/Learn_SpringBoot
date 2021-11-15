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
