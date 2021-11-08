# yaml/yml 配置文件
```yaml
# springboot配置文件到底能配置哪些属性呢?

# 可以注入到我们的配置类中

### 普通键值对
# properties:  key=value
# yml/yaml: key: value

### 存对象
#student:
#  name: kindred
#  age: 1500

# 行内写法
#student: {name: kindred,age: 1500}

### 数组(List,Set)
#names:
#  - kindred
#  - gnar
#  - neeko

# 行内写法
#names: [kindred,gnar,neeko]

### map
#tes:
#  maps: {key1: 12,key2: 34}

# 行内写法
#tes:
#  maps:
#    key1: 15
#    key2: 2



########################################

#champion:
#  name: kindred
#  age: 1500
#  skills:
#    - Q
#    - W
#    - E
#    - R

master:
  name: QSJ
  age: 24
  sex: true
  birth: 2021/11/08
  maps: {k1: v1,k2: v2}
  champion:
    name: kindred
    age: 1500
    skills:
      - Q-乱箭之舞
      - W-狼灵狂热
      - E-俱意横生
      - R-羊灵生息
```

# 获取配置文件中的参数
  - 方式一：使用@ConfigurationProperties(prefix = "xxxxx")直接绑定yml文件中对应名称的属性  
  要求：对象的属性要与yml中的属性名相同
  
  - 凡是二：@PropertySource(value = "classpath:gnar.yml",encoding = "UTF-8")与@Value  
  可以自定义配置文件名称和路径，可以解析properties与yml
```java
package com.mildlamb.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
//@ConfigurationProperties(prefix = "master")

//加载指定的配置文件,任意名称配置文件
@PropertySource(value = "classpath:gnar.yml",encoding = "UTF-8")
public class Master {
    //用SPEL指定值
    @Value("${name}")
    private String name;
    private Integer age;
    private Boolean sex;
    //如果用的LocalDate类，需要注解：@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birth;
    private Map<String,Object> maps;
    private Champion champion;

    ...  ...

```
