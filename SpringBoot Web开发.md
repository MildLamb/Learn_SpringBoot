# SpringBoot Web开发
## 需要解决的问题
- 如何导入静态资源
- 首页
- 页面，模板引擎Thymeleaf
- 装配扩展SpringMVC
- CRUD
- 拦截器
- 国际化

### 静态资源
- 在springboot中，我们可以在如下位置中放置静态资源 localhost:8080/资源名称
```java
// WebProperties类中
private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
				"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
        
//WebMvcProperties类中
private String staticPathPattern = "/**";
```
- 或者使用webjars，需要导入对应的依赖 localhost:8080/webjars/...
```xml
<!-- 使用webjars导入jquery -->
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>alpaca</artifactId>
    <version>1.5.24</version>
</dependency>
```
- 自定义，静态资源访问路径
```properties
# 自定义静态资源地址
#spring.mvc.static-path-pattern=/hello/**
```

## thymeleaf

thymeleaf使用:https://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html#attribute-precedence

- 导入依赖
```xml
<!-- 导入thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
- thymeleaf使用
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
    <!-- 所有的html元素都可以被 thymeleaf 替换接管 th:元素名 -->
    <h1 th:text="${msg}">首页</h1>
    <h1 th:utext="${msg}">首页</h1>

    <h3 th:each="champion:${champions}" th:text="${champion}">[[${champion}]]</h3>
</body>
</html>
```
## 扩展SpringMVC
```java
//如果想自定义一些定制化功能，只需要写对应的组件，将自己写的组件交给springboot，springboot就会帮我们自动装配
//如果我们要扩展springMVC，官方建议我们这样做
//声明一个配置类@Configuration  实现各种 XxxConfigurer接口


//全面扩展SpringMVC
@Configuration
@EnableWebMvc  //如果想接管配置  一定不能加这个注解  因为WebMvcAutoConfiguration 有 @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
public class MvcConfig implements WebMvcConfigurer {

    //视图跳转

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/mildlamb").setViewName("test");
    }


//    @Bean
//    public ViewResolver getMyViewResolver(){
//        return new MyViewResolver();
//    }
//
//    public static class MyViewResolver implements ViewResolver {
//        @Override
//        public View resolveViewName(String viewName, Locale locale) throws Exception {
//            return null;
//        }
//    }
}
```

## 拦截器 
- 自定义拦截器实现handlerInterceptor接口 重写preHandle方法
```java
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //登录成功后应该有用户的session
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser != null){
            return true;
        }else {
            request.setAttribute("msg","没有权限，请先登录");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```
- 配置自定义拦截器
```java
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //自定义国际化组件
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

		//配置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index","/","/index.html","/user/login","/css/*","/js/*","/img/*");
    }
}
```
