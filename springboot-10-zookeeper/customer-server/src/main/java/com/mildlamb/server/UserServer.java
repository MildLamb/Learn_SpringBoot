package com.mildlamb.server;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class UserServer {
    //想拿到provider提供的票，到注册中心获取服务
    //本地是@Autowirse
    //远程是@DubboReference
    /*
        我们这个项目没有提供者的TicketServer类怎么办：
            - pom坐标，可以定义路径相同的接口名
     */
    @DubboReference  //引用
    TicketServer ticketServer;

    public void buyTicket(){
        String ticket = ticketServer.getTicket();
        System.out.println("在注册中心 ==> " + ticket);
    }
}
