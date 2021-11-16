package com.mildlamb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    /*
        shiro三大对象:
            1. Subject 用户对象
            2. SecurityManager  管理用户对象
            3. Realm  数据连接
     */

    @RequestMapping({"/index","/","/index.html"})
    public String toIndex(Model model){
        model.addAttribute("msg","Hello Shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,@RequestParam("password") String password,Model model){
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();

        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        //执行登录方法，如果没有异常，说明登录失败
        try {
            subject.login(token);
            return "index";
        } catch(UnknownAccountException e){  //如果用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "login";
        } catch(IncorrectCredentialsException e){  //密码不正确
            model.addAttribute("msg","密码不正确");
            return "login";
        } catch (AuthenticationException e) {
            return "login";
        }
    }

    @RequestMapping("/to401")
    @ResponseBody
    public String Unauthorized(){
        return "未授权访问该页面";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

}
