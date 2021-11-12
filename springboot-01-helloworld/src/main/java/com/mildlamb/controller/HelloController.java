package com.mildlamb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/kind")
    @ResponseBody
    public String hello(){
        return "Hello,Kindred";
    }
}
