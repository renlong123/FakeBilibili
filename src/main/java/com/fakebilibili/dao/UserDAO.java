package com.fakebilibili.dao;

import com.fakebilibili.entity.User;
import org.springframework.stereotype.Repository;

public interface UserDAO {

    public User selectUserById(Integer id);

    public int insertUser(User user);

    public int modifyUser(User user);

    public int deleteUserById(Integer id);

    public User selectUserByName(String username);

}
