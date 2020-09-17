package com.fakebilibili.dao;

import com.fakebilibili.entity.User;
import com.fakebilibili.entity.UserToUser;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserDAO {

    public User selectUserById(Integer id);

    public int insertUser(User user);

    public int modifyUser(User user);

    public int deleteUserById(Integer id);

    public User selectUserByName(String username);

    public Integer checkFollowStatus(Integer userupId,Integer userfollowsId);

    public int insertUsertoUser(UserToUser usertoUser);

    public int deleteUsertoUserById(Integer id);

    public List<User> getAllUsers();
}
