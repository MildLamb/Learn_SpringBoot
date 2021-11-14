package com.mildlamb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootTest
class Springboot05DataApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        //查看对应的数据源
        System.out.println(dataSource.getClass());

//        Connection connection = dataSource.getConnection();
//        String sql = "select * from user";
//        System.out.println(connection);
//        connection.close();
    }

}
