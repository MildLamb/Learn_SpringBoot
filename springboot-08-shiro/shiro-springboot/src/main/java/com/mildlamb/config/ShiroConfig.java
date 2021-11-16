package com.mildlamb.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //3.ShiroFilterFactoryBean
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroBean(@Autowired DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroBean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
            anon：无需认证就能访问
            authc：必须认证了才能访问
            user：必须拥有记住我功能才能访问
            perms：拥有对某个资源的权限才能访问
            role：拥有某个角色才能访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String, String>();
        //拦截规则
        filterMap.put("/user/add","anon");
        filterMap.put("/user/update","authc");
        //授权
        //表示 必须是user用户 并且有add权限
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        // 正常情况 如果没授权 需要跳转到一个没授权的页面 否则就是返回401状态码
        shiroBean.setUnauthorizedUrl("/to401");



        shiroBean.setFilterChainDefinitionMap(filterMap);

        //设置登录页面,也就是没有认证后跳转的页面
        shiroBean.setLoginUrl("/toLogin");

        return shiroBean;
    }


    //2.DefaultWebSecurityManager
    @Bean("shiroManager")
    public DefaultWebSecurityManager getManager(@Autowired UserRealm userRealm,@Autowired CookieRememberMeManager rememberMeManager){
        DefaultWebSecurityManager Manager = new DefaultWebSecurityManager();
        //关联UserRealm
        Manager.setRealm(userRealm);
        Manager.setRememberMeManager(rememberMeManager);
        return Manager;
    }

    //1.创建 realm 对象，需要自定义
    @Bean("userRealm")
    public UserRealm getUserRealm(){
        return new UserRealm();
    }

    //整合ShiroDialect：用来整合 shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

}
