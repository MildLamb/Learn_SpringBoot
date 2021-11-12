package com.mildlamb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

//在templates目录下的所有页面，只能通过controller去访问
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","<h1>Hello,Kindred</h1>");
        model.addAttribute("champions", Arrays.asList("kindred","gnar","neeko"));
        return "index";
    }
}
