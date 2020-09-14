package com.fakebilibili.controller;

import com.fakebilibili.entity.Commits;
import com.fakebilibili.service.CommitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import com.baidu.ueditor.ActionEnter;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private CommitsService commitsService;

    @RequestMapping(value = "/ueditor",method = RequestMethod.GET)
    public String ueditorTest(){
        return "test";
    }

    /**
     * 插入评论
     * @param commits
     * @return
     */
    @RequestMapping(value = "/Commits",method = RequestMethod.POST)
    @ResponseBody
    public String insertCommits(Commits commits){
        System.out.println(commits);
        int i = commitsService.insertCommits(commits);
        if(i>=1){
            return "true";
        }
        return "fasle";
    }

    @RequestMapping(value = "/Commits/video/{videoId}",method = RequestMethod.GET)
    @ResponseBody
    public String getCommitsById(@PathVariable("videoId") Integer videoId,Integer startPage,Integer pageSize){
        System.out.println(videoId+"=="+startPage+"===="+pageSize);
        String s = commitsService.selectCommitsByVideoId(videoId, startPage, pageSize);
        return s;
    }
}
