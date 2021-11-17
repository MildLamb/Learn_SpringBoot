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
- 配置Swagger => Config
```java
@Configuration
@EnableSwagger2  //开启Swagger2
public class SwaggerConfig {
    private static final Contact DEFAULT_CONTACT = new Contact("Engulf迷失", "http://gnardada.com", "1216982545@qq.com");

    //配置Swagger的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo());
    }

    //配置swagger的一些信息
    private ApiInfo getApiInfo(){
        return new ApiInfo("MildLamb开发API介绍", "这小羊有点温柔", "version-1.0", "gnardada.com",
                DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
    }
}
```
