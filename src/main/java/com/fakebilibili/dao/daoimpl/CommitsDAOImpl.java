package com.fakebilibili.dao.daoimpl;

import com.fakebilibili.mapper.CommitsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class CommitsDAOImpl {

    @Autowired
    private CommitsMapper commitsMapper;

}
