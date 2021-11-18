package com.mildlamb.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    //想要网页不延迟，就要用spring boot的注解标注这是一个异步任务,记得在主启动类开启异步支持
    @Async
    public void task(){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据正在处理");
    }
}
