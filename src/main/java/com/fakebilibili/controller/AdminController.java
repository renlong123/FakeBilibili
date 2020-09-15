package com.fakebilibili.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String jumpToAdmin(){
        return "admin/adminIndex";
    }

}
