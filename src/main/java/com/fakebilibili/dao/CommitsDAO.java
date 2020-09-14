package com.fakebilibili.dao;

import com.fakebilibili.entity.Commits;

import java.util.List;

public interface CommitsDAO {

    public int insertOneCommits(Commits commits);

    public int updateOneCommits(Commits commits);

    public int deleteOneCommitsById(Integer id);

    public Commits selectOneCommitsById(Integer id);

    public List<Commits> selectCommitsByVideoId(Integer videoId);

    public List<Commits> selectCommitsByUserId(Integer userId);

}
