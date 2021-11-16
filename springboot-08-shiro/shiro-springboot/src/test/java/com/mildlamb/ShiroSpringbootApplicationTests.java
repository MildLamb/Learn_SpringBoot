package com.mildlamb;

import com.mildlamb.pojo.User;
import com.mildlamb.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void contextLoads() {
        User kindred = userService.selectUserByName("Kindred");
        System.out.println(kindred);
    }

}
