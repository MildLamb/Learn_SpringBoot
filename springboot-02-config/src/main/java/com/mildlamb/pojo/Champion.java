package com.mildlamb.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "champion")
public class Champion {

    private String lastName;

    private Integer age;

    private List<String> skills;

    public Champion() {
    }

    public Champion(String lastName, Integer age, List<String> skills) {
        this.lastName = lastName;
        this.age = age;
        this.skills = skills;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "lastName='" + lastName + '\'' +
                ", age=" + age +
                ", skills=" + skills +
                '}';
    }
}
