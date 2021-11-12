package com.mildlamb.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "master")
@Validated  //数据校验

//加载指定的配置文件,任意名称配置文件
//@PropertySource(value = "classpath:gnar.yml",encoding = "UTF-8")
public class Master {
    //用SPEL指定值
//    @Value("${name}")
    private String name;
    private Integer age;
    private Boolean sex;
    //如果用的LocalDate类，需要注解：@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birth;
    private Map<String,Object> maps;
    private Champion champion;

    public Master() {
    }

    public Master(String name, Integer age, Boolean sex, Date birth, Map<String, Object> maps, Champion champion) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birth = birth;
        this.maps = maps;
        this.champion = champion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    @Override
    public String toString() {
        return "Master{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", birth=" + birth +
                ", maps=" + maps +
                ", champion=" + champion +
                '}';
    }
}
