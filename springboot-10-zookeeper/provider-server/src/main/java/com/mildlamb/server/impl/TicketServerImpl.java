package com.mildlamb.server.impl;

import com.mildlamb.server.TicketServer;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@DubboService
@Component //使用了Dubbo后尽量不要使用@Service注解
public class TicketServerImpl implements TicketServer {
    @Override
    public String getTicket() {
        return "得到一张票";
    }
}
