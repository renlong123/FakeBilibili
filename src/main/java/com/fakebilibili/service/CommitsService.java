package com.fakebilibili.service;

import com.fakebilibili.entity.Commits;

import java.util.List;

public interface CommitsService {

    public String selectCommitsByVideoId(Integer videoId,Integer startPage,Integer pageSize);

    public int insertCommits(Commits commits);

}
