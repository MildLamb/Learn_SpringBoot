package com.mildlamb.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    //在一个特定的时间执行这个方法
    /*
        cron表达式:

     */
    // 秒 分 时 日 月 星期
    @Scheduled(cron = "0 37 15 * * ?")
    public void hello(){
        System.out.println("Hello,方法已执行");
    }
}
