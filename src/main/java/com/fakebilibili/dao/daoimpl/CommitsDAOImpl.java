package com.fakebilibili.dao.daoimpl;

import com.fakebilibili.dao.CommitsDAO;
import com.fakebilibili.entity.Commits;
import com.fakebilibili.mapper.CommitsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommitsDAOImpl implements CommitsDAO {

    @Autowired
    private CommitsMapper commitsMapper;

    @Override
    public int insertOneCommits(Commits commits) {
        int insert = commitsMapper.insert(commits);
        return insert;
    }

    @Override
    public int updateOneCommits(Commits commits) {
        int i = commitsMapper.updateByPrimaryKey(commits);
        return i;
    }

    @Override
    public int deleteOneCommitsById(Integer id) {
        int i = commitsMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public Commits selectOneCommitsById(Integer id) {
        Commits commits = commitsMapper.selectByPrimaryKey(id);
        return commits;
    }

    @Override
    public List<Commits> selectCommitsByVideoId(Integer videoId) {
        List<Commits> commits = commitsMapper.selectCommitsByVideoId(videoId);
        return commits;
    }

    @Override
    public List<Commits> selectCommitsByUserId(Integer userId) {
        return null;
    }
}
