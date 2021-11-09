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
