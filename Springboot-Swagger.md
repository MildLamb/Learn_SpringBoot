# Swagger
- 号称是世界上最流行的Api框架
- RestFul Api 文档在线自动生成工具 => Api文档与Api定义同步更新
- 直接运行，可以在线测试API接口
- 支持多种语言：(Java,php...)

## 使用Swagger需要用到
- swagger2
- ui

# Springboot集成Swagger
- 导入相关依赖
```xml
<!-- Swagger -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```
## Swagger配置扫描接口
- 配置Swagger => Config
```java
@Configuration
@EnableSwagger2  //开启Swagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT = new Contact("Engulf迷失", "http://gnardada.com", "1216982545@qq.com");

    //配置Swagger的bean实例
    @Bean
    public Docket docket(Environment environment){

        //如果当前环境是dev，则会返回一个Profiles对象，我们判断这个返回值是否为空，来实现判断当前环境是否为dev环境
        Profiles profiles = Profiles.of("dev");
        //判断当前环境是否为dev环境
        boolean b = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                //实现在开发环境启动swagger，其他环境关闭swagger
                .enable(b)
                .select()
                //RequestHandlerSelectors  配置要扫描的接口位置
                /*
                    basePackage(""):指定要扫描的包
                    any():扫描全部
                    none():不扫描
                    withMethodAnnotation(final Class<? extends Annotation> annotation):扫描方法上的注解，参数是一个注解的反射对象
                    withClassAnnotation(final Class<? extends Annotation> annotation):扫描类上的注解
                 */
                .apis(RequestHandlerSelectors.basePackage("com.mildlamb.controller"))
                //paths():过滤扫描路径
                //只有是/mildlamb下的请求才能被扫描到
//                .paths(PathSelectors.ant("/mildlamb/**"))
                .build();
    }

    //配置swagger的一些信息
    private ApiInfo getApiInfo(){
        return new ApiInfo("MildLamb开发API介绍", "这小羊有点温柔", "version-1.0", "gnardada.com",
                DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
    }
}

```
