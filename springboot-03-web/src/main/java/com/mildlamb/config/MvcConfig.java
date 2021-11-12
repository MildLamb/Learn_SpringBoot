package com.mildlamb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

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

