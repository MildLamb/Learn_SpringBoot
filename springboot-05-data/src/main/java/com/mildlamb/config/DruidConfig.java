package com.mildlamb.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控
    // 因为springboot内置了servlet容器，所以没有web.xml 替代方法 ：ServletRegistrationBean
    @Bean
    public ServletRegistrationBean a(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //配置后台账号密码
        HashMap<String,String> initParameters = new HashMap<>();
        //添加配置 key是固定的 可以到ResourceServlet类中找到
        //配置用户名密码
        initParameters.put("loginUsername","MildLamb");
        initParameters.put("loginPassword","W2kindred");
        //配置允许谁能访问
        initParameters.put("allow","localhost");


        bean.setInitParameters(initParameters); //设置初始化参数
        return bean;
    }

    //filter
    @Bean
    public FilterRegistrationBean webFilter(){
        FilterRegistrationBean<Filter> FilterBean = new FilterRegistrationBean<>();
        FilterBean.setFilter(new WebStatFilter());

        //过滤哪些请求
        Map<String,String> initParameters = new HashMap<>();
        initParameters.put("exclusions","*.js,*.css,*.img,/druid/*");

        FilterBean.setInitParameters(initParameters);
        return FilterBean;
    }
}
