package com.fakebilibili.service;

import com.fakebilibili.entity.Video;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public interface VideoService {

    public int insertVideo(HttpSession session,
                           String name, String description, String categoryId,
                           MultipartFile[] uploadVideoInfo) throws IOException;

    public String getVideoInfoByUserId(Integer userId,int page,int pageSize);

    public String getOneVideoById(Integer id);

    public int deleteVideoById(Integer id);

    public String getVideoAndOtherInfo(Integer id, Model model);

    public String getVideoInfo(Integer authorid,Integer id);

    public List<Video> getAllVidesInfo();
}
