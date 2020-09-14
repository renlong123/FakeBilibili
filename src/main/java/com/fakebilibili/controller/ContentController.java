package com.fakebilibili.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import com.baidu.ueditor.ActionEnter;

@Controller
public class ContentController {

    @RequestMapping(value = "/ueditor",method = RequestMethod.GET)
    public String ueditorTest(){
        return "test";
    }

    @RequestMapping(value = "/Commits",method = RequestMethod.POST)
    public String insertCommits(){
        return null;
    }
}
