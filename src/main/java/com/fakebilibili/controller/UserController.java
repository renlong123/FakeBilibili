package com.fakebilibili.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/login")
    public String userLogin(){
        return "login";
    }

    @RequestMapping("/register")
    public String userRegister(){
        return "register";
    }
}
