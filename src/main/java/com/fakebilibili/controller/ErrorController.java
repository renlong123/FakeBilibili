package com.fakebilibili.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    Logger logger = LogManager.getLogger(ErrorController.class);

    @RequestMapping("/404")
    public String page404(Model model){
        logger.error("404错误，页面找不到");
        model.addAttribute("errorCode",400);
        model.addAttribute("msg","页面找不到，可能是网址已失效或网址输入错误");
        return "error/404";
    }

    @RequestMapping("/500")
    public String page500(Model model){
        logger.error("500错误，服务器内部错误");
        model.addAttribute("errorCode",500);
        model.addAttribute("msg","服务器内部发生错误，请与管理员联系");
        return "error/defaultError";
    }

}
