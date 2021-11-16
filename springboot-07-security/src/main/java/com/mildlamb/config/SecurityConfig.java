package com.mildlamb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

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


        /*
            1. login-page 自定义登录页url,默认为/login
            2. login-processing-url 登录请求拦截的url,也就是form表单提交时指定的action
            3. default-target-url 默认登录成功后跳转的url
            4. always-use-default-target 是否总是使用默认的登录成功后跳转url
            5. authentication-failure-url 登录失败后跳转的url
            6. username-parameter 用户名的请求字段 默认为userName
            7. password-parameter 密码的请求字段 默认为password
         */
        //没有权限默认会跳转到security登录页面
        //开启登录页面     定制登录页
        http.formLogin().loginPage("/toLogin").usernameParameter("user").passwordParameter("psw").loginProcessingUrl("/login");
        //和前端传递的用户名，密码参数对应

        //开启注销功能
        http.logout().logoutSuccessUrl("/");
        //注销成功后跳到首页

        //开启记住我功能 cookie  默认保存2天
        http.rememberMe().rememberMeParameter("remember");
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
