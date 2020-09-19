/*
package com.fakebilibili;

import com.fakebilibili.dao.UserDAO;
import com.fakebilibili.dao.daoimpl.UserDAOImpl;
import com.fakebilibili.entity.User;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MBGTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private UserDAO userdao = new UserDAOImpl();

    @Test
    public void generateAuto()throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("src/main/resources/MBGconfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    @Test
    public void getUserByName(){
        String username = "tom";
        User user = userdao.selectUserByName(username);
        System.out.println(user);
    }
}
*/
