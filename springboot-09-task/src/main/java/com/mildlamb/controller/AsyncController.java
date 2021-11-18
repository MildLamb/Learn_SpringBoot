package com.mildlamb.controller;

import com.mildlamb.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    @Autowired
    private AsyncService service;

    @GetMapping("/task")
    public String async(){
        service.task();
        return "OK";
    }
}
