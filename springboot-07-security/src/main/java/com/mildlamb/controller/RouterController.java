package com.mildlamb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SuppressWarnings("all")
public class RouterController {
    @RequestMapping({"/","/index","/index.html"})
    public String index(){
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "views/login";
    }

    @RequestMapping("/logiout")
    public String toLogout(){
        return "index";
    }

    @RequestMapping("/level1/{pid}")
    public String toPage(@PathVariable("pid") int id){
        return "views/level1/" + id;
    }

    @RequestMapping("/level2/{pid}")
    public String toPage2(@PathVariable("pid") int id){
        return "views/level2/" + id;
    }

    @RequestMapping("/level3/{pid}")
    public String toPage3(@PathVariable("pid") int id){
        return "views/level3/" + id;
    }
}
