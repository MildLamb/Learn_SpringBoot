package com.mildlamb;

import com.mildlamb.server.UserServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServerApplicationTests {

    @Autowired
    private UserServer userServer;

    @Test
    void contextLoads() {
        userServer.buyTicket();
    }

}
