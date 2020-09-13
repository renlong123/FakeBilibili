package com.fakebilibili.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VideoController {


    @RequestMapping(value = "/videoUploadPage/{id}",method = RequestMethod.GET)
    public String jumpToVideoUploadPage(@PathVariable(value = "id",required = true)String id){
        return "videoUploadPage";
    }

}
