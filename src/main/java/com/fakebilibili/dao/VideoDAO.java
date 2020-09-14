package com.fakebilibili.dao;

import com.fakebilibili.entity.Video;

import java.util.List;
import java.util.Map;

public interface VideoDAO {

    public int insertVideo(Video video);

    public int updateVideoById(Video video);

    public int deleteVideoById(Integer id);

    public Video getOneVideoById(Integer id);

    public List<Video> getVideoInfoByUserId(Integer userId);

    public List<Video> getVideosInfoExceptsId(Integer authorid, Integer id);
}
