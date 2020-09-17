package com.fakebilibili.service;

import com.fakebilibili.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface UserService {

    public String userLogin(HttpServletRequest request, String username, String password,
                            String remenberMe, HttpServletResponse response);

    public void getCheckCode(HttpServletRequest request,HttpServletResponse response) throws IOException;

    public String userLoginOut(String username, HttpServletRequest request, HttpServletResponse response);

    public int insertUserSelective(User user);

    public String getEmailCheckResp(String email,HttpSession session);

    public String getEmailCheckValResp(String emailCheckCode,HttpSession session);

    public String putUserInfoToSession(HttpSession session);

    public int updateUserOptional(User user,HttpSession session);

    public String checkFollowStatus(Integer userupId,Integer userfollowsId);

    public String changeFollowedStatus(Integer userupId,Integer userfollowsId);

    public String getAllUsers(Model model, Integer page, Integer pageSize);
}
