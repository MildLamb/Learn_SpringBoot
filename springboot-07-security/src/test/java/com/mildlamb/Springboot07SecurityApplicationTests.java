package com.mildlamb;

import com.mildlamb.config.PWDEncoding;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot07SecurityApplicationTests {

    @Autowired
    private PWDEncoding pwdEncoding;

    @Test
    void contextLoads() {
        System.out.println(pwdEncoding.encodePassword("q1216982545"));
    }

}
