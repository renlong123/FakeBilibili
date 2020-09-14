package com.fakebilibili.service.serviceimpl;

import com.fakebilibili.dao.CommitsDAO;
import com.fakebilibili.entity.Commits;
import com.fakebilibili.entity.Video;
import com.fakebilibili.service.CommitsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitsServiceImpl implements CommitsService {

    @Autowired
    private CommitsDAO commitsDAO;

    @Override
    public String selectCommitsByVideoId(Integer videoId,Integer startPage,Integer pageSize) {
        PageHelper.startPage(startPage,pageSize);
        List<Commits> commits = commitsDAO.selectCommitsByVideoId(videoId);
        PageInfo<Commits> pageInfo = new PageInfo<>(commits);
        Gson gson = new Gson();
        String json = gson.toJson(pageInfo);
        return json;
    }

    @Override
    public int insertCommits(Commits commits) {
        commits.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
        commits.setLastModified(new java.sql.Date(System.currentTimeMillis()));
        System.out.println(commits);
        int i = commitsDAO.insertOneCommits(commits);
        return i;
    }
}
