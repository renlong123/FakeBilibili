package com.fakebilibili.dao.daoimpl;

import com.fakebilibili.dao.UserDAO;
import com.fakebilibili.entity.User;
import com.fakebilibili.entity.UserExample;
import com.fakebilibili.entity.UserToUser;
import com.fakebilibili.mapper.UserMapper;
import com.fakebilibili.mapper.UserToUserMapper;
import org.junit.jupiter.api.DynamicTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserToUserMapper userToUserMapper;

    /**
     * 数据库访问层通过Id查询用户
     * @param id
     * @return
     */
    @Override
    public User selectUserById(Integer id) {
        System.out.println(userMapper);
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 数据库访问层添加用户
     * @param user
     * @return
     */
    @Override
    public int insertUser(User user) {
        int result = userMapper.insert(user);
        return result;
    }

    /**
     * 数据库访问层修改用户，选择性修改，当某一项为空时则不进行修改。
     * @param user
     * @return
     */
    @Override
    public int modifyUser(User user) {
        int result = userMapper.updateByPrimaryKeySelective(user);
        return result;
    }

    /**
     * 数据库访问层删除用户（通过id删除）
     * @param id
     * @return
     */
    @Override
    public int deleteUserById(Integer id) {
        int result = userMapper.deleteByPrimaryKey(id);
        return result;
    }

    /**
     * 数据库访问层根据用户名查找用户
     * @param username
     * @return
     */
    @Override
    public User selectUserByName(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(username != null){
            criteria.andNameEqualTo(username);
        }
        List<User> user = userMapper.selectByExample(userExample);
        if(user.size()>=1){
            return user.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer checkFollowStatus(Integer userupId, Integer userfollowsId) {
        Integer i = userToUserMapper.selectFollowStatus(userupId, userfollowsId);
        System.out.println(i);
        return i;
    }

    @Override
    public int insertUsertoUser(UserToUser userToUser) {
        int insert = userToUserMapper.insert(userToUser);
        return insert;
    }

    @Override
    public int deleteUsertoUserById(Integer id) {
        int i = userToUserMapper.deleteByPrimaryKey(id);
        return i;
    }
}
