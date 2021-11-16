package com.mildlamb.config;

import com.mildlamb.pojo.User;
import com.mildlamb.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义Realm 继承 AuthorizingRealm类
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了 ==> 认证方法");

        //subject.login(token);被调用  这里的参数token就可以获取到用户的token
        //userToken 是前端传递的 用户名密码 也就是我们需要验证的用户名和密码
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        userToken.setRememberMe(true);

        //根据前端传递的用户名 查询数据库是否存在用户 是-返回用户对象
        User user = userService.selectUserByName(userToken.getUsername());
        if (user != null){
            //密码认证 shiro去完成  将用户数据库中的密码当作参数传递给shiro去进行验证
            // 密码可以加密
            // 这里的第一个参数传递user后，可以在授权中获取到user对象
            System.out.println("认证: ==> " + user);
            return new SimpleAuthenticationInfo(user,user.getPwd(),"");
        }else{
            return null;   //return null 会报出 UnknownAccountException 异常
        }
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了 ==> 授权方法");

        //给用户授予权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.addStringPermission("user:add");

        //获取当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        //拿到User对象，从认证方法return new SimpleAuthenticationInfo(user,user.getPwd(),""); 第一个参数中获取到
        User currentUser = (User) subject.getPrincipal();
        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());
        System.out.println("授权 ==> " + currentUser);
        System.out.println(currentUser.getPerms());


        return info;
    }

}
