# Shiro
### quick start
- 导入依赖
```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-core</artifactId>
    <version>1.7.1</version>
</dependency>

<!-- configure logging -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>1.7.25</version>
</dependency>
```
- 配置文件
  - log4j.properties
```properties
log4j.rootLogger=INFO, stdout

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m %n

# General Apache libraries
log4j.logger.org.apache=WARN

# Spring
log4j.logger.org.springframework=WARN

# Default Shiro logging
log4j.logger.org.apache.shiro=INFO

# Disable verbose logging
log4j.logger.org.apache.shiro.util.ThreadContext=WARN
log4j.logger.org.apache.shiro.cache.ehcache.EhCache=WARN
```
  - shiro.ini
```ini
[users]
# user 'root' with password 'secret' and the 'admin' role
root = secret, admin
# user 'guest' with the password 'guest' and the 'guest' role
guest = guest, guest
# user 'presidentskroob' with password '12345' ("That's the same combination on
# my luggage!!!" ;)), and role 'president'
presidentskroob = 12345, president
# user 'darkhelmet' with password 'ludicrousspeed' and roles 'darklord' and 'schwartz'
darkhelmet = ludicrousspeed, darklord, schwartz
# user 'kindred' with password 'W2kindred' and roles 'goodguy' and 'schwartz'
kindred = W2kindred, goodguy, schwartz

# -----------------------------------------------------------------------------
# Roles with assigned permissions
#
# Each line conforms to the format defined in the
# org.apache.shiro.realm.text.TextConfigurationRealm#setRoleDefinitions JavaDoc
# -----------------------------------------------------------------------------
[roles]
# 'admin' role has all permissions, indicated by the wildcard '*'
admin = *
# The 'schwartz' role can do anything (*) with any lightsaber:
schwartz = lightsaber:*
# The 'goodguy' role is allowed to 'drive' (action) the winnebago (type) with
# license plate 'eagle5' (instance specific id)
goodguy = winnebago:drive:eagle5
```
- 测试
```java
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


    public static void main(String[] args) {

        // The easiest way to create a Shiro SecurityManager with configured
        // realms, users, roles and permissions is to use the simple INI config.
        // We'll do that by using a factory that can ingest a .ini file and
        // return a SecurityManager instance:

        // Use the shiro.ini file at the root of the classpath
        // (file: and url: prefixes load from files and urls respectively):
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        securityManager.setRealm(iniRealm);

        // for this simple example quickstart, make the SecurityManager
        // accessible as a JVM singleton.  Most applications wouldn't do this
        // and instead rely on their container configuration or web.xml for
        // webapps.  That is outside the scope of this simple quickstart, so
        // we'll just do the bare minimum so you can continue to get a feel
        // for things.
        SecurityUtils.setSecurityManager(securityManager);

        // Now that a simple Shiro environment is set up, let's see what you can do:

        // get the currently executing user:
        // 获取当前的用户对象Subject
        Subject currentUser = SecurityUtils.getSubject();

        // Do some stuff with a Session (no need for a web or EJB container!!!)
        // 通过当前用户拿到Session
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        // let's login the current user so we can check against roles and permissions:
        // 判断当前用户是否被认证
        if (!currentUser.isAuthenticated()) {
            // token：令牌
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");  //ini中配置
            token.setRememberMe(true);  //设置记住我
            try {
                currentUser.login(token);  //执行登录操作
            } catch (UnknownAccountException uae) {  //用户名错误
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {  //密码错误
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //test a role:
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        //粗粒度
        //test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //细粒度
        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        //注销
        currentUser.logout();

        //结束
        System.exit(0);
    }
}

```
- 当前用户对象subject的主要方法
  - 获取当前对象：SecurityUtils.getSubject();
  - 获取当前用户Session，不是httpsession：currentUser.getSession();
  - 判断当前用户是否被认证：currentUser.isAuthenticated()
  - 获取当前用户的认证：currentUser.getPrincipal()
  - 判断用户是否拥有角色：currentUser.hasRole("schwartz")
  - 判断用户是否有权限：currentUser.isPermitted("lightsaber:wield")

# springboot 整合 shiro
- 导入依赖
```xml
<!-- shiro整合springboot的依赖 -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-web-starter</artifactId>
    <version>1.7.1</version>
</dependency>
```
- 自定义Realm类 继承 AuthorizingRealm类
```java
//自定义Realm 继承 AuthorizingRealm类
public class UserRealm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了 ==> 授权方法");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了 ==> 认证方法");
        //伪造一个用户数据
        String name = "kindred";
        String pwd = "W2kindred";

        //subject.login(token);被调用  这里的参数token就可以获取到用户的token
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        if (!userToken.getUsername().equals(name)){
            return null;  //抛出异常 UnknownAccountException
        }

        //密码认证 shiro去完成
        return new SimpleAuthenticationInfo("",pwd,"");
    }
}
```
- 自定义Shiro配置
```java
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
        // 正常情况 如果没授权 需要跳转到一个没授权的页面 否则就是返回401状态码
        shiroBean.setUnauthorizedUrl("/to401");



        shiroBean.setFilterChainDefinitionMap(filterMap);

        //设置登录页面,也就是没有认证后跳转的页面
        shiroBean.setLoginUrl("/toLogin");

        return shiroBean;
    }


    //2.DefaultWebSecurityManager
    @Bean("shiroManager")
    public DefaultWebSecurityManager getManager(@Autowired UserRealm userRealm){
        DefaultWebSecurityManager Manager = new DefaultWebSecurityManager();
        //关联UserRealm
        Manager.setRealm(userRealm);
        return Manager;
    }

    //1.创建 realm 对象，需要自定义
    @Bean("userRealm")
    public UserRealm getUserRealm(){
        return new UserRealm();
    }
}
```
- Controller接收用户登录信息，调用登录方法
```java
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

}
```
