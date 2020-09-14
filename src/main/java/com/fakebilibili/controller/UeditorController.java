package com.fakebilibili.controller;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UeditorController {
    @RequestMapping(value = "/config",method = RequestMethod.GET)
    public void config(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        request.setCharacterEncoding( "UTF-8" );
        response.setHeader("Content-Type" , "text/html");
        PrintWriter writer = response.getWriter();
        /*config.json的目录*/
        String rootPath = request.getServletContext().getContextPath()+"/static/ueditor/jsp";//;
        System.out.println(rootPath);
        writer.write( new ActionEnter( request, rootPath ).exec() );
        writer.flush();
        writer.close();
    }
}
