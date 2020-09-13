package com.fakebilibili.controller;

import com.fakebilibili.entity.User;
import com.fakebilibili.entity.Video;
import com.fakebilibili.service.VideoService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 点击新增视频后跳转到视频插入页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/videoUploadPage/{id}",method = RequestMethod.GET)
    public String jumpToVideoUploadPage(@PathVariable(value = "id",required = true)String id){
        return "videoUploadPage";
    }

    /**
     * 插入视频信息
     * @param session
     * @param name
     * @param description
     * @param categoryId
     * @param uploadVideoInfo
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadVideo",method = RequestMethod.POST)
    public String insertVideoIntoDatabase(HttpSession session,
                                          String name,String description,String categoryId,
                                          @RequestParam MultipartFile[] uploadVideoInfo) throws IOException {
        int i = videoService.insertVideo(session, name, description, categoryId, uploadVideoInfo);
        if(i>=1){
            return "personIndex";
        }else{
            return "videoUploadPage";
        }
    }

    /**
     * 获取视频信息以JSON返回
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getVideoInfo/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public String getVideoInfoByUserId(@PathVariable("userId")String userId,@RequestParam("page")String page,
                                       @RequestParam("pageSize")String pageSize){
        return videoService.getVideoInfoByUserId(Integer.parseInt(userId),Integer.parseInt(page),Integer.parseInt(pageSize));
    }


    @RequestMapping(value = "/getOneVideoInfo/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getVideoInfoById(@PathVariable("id")String id){
        return videoService.getOneVideoById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/deleteOneVideoInfo/{id}",method = RequestMethod.DELETE)
    public String deleteVideoInfoById(@PathVariable("id")String id){
        int i = videoService.deleteVideoById(Integer.parseInt(id));
        if(i>=1){
            return "true";
        }else{
            return "false";
        }
    }
}
