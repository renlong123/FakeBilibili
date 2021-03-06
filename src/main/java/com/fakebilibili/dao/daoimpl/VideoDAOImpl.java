package com.fakebilibili.dao.daoimpl;

import com.fakebilibili.dao.VideoDAO;
import com.fakebilibili.entity.Video;
import com.fakebilibili.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.ws.soap.Addressing;
import java.util.List;

@Repository
public class VideoDAOImpl implements VideoDAO {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public int insertVideo(Video video) {
        int insert = videoMapper.insert(video);
        return insert;
    }

    @Override
    public int updateVideoById(Video video) {
        return 0;
    }

    @Override
    public int deleteVideoById(Integer id) {
        int i = videoMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public Video getOneVideoById(Integer id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        return video;
    }

    @Override
    public List<Video> getVideoInfoByUserId(Integer userId) {

        List<Video> videoInfoByUserId = videoMapper.getVideoInfoByUserId(userId);

        return videoInfoByUserId;
    }

    @Override
    public List<Video> getVideosInfoExceptsId(Integer authorid, Integer id) {
        System.out.println(authorid + "=======查询前=======" +id);
        List<Video> videos = videoMapper.getVideosInfoExceptsId(authorid, id);
        for(Video video:videos){
            System.out.println(video.getId() + "=======查询后=======" +video.getAuthorid());
        }

        return videos;
    }

    @Override
    public List<Video> getIndexVideosInfo(){
        List<Video> videos = videoMapper.getIndexVideosInfo();
        return videos;
    }
}
