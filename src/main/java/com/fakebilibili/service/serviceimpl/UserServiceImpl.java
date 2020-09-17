package com.fakebilibili.service.serviceimpl;

import com.fakebilibili.dao.UserDAO;
import com.fakebilibili.dao.daoimpl.UserDAOImpl;
import com.fakebilibili.entity.User;
import com.fakebilibili.entity.UserToUser;
import com.fakebilibili.mapper.UserMapper;
import com.fakebilibili.mapper.UserToUserMapper;
import com.fakebilibili.service.UserService;
import com.fakebilibili.util.ImageUtil;
import com.fakebilibili.util.MailUtil;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    /**
     * 处理用户登录请求及进入登录页面请求，
     * @param request
     * @param username
     * @param password
     * @param remenberMe
     * @param response
     * @return
     */
    @Override
    public String userLogin(HttpServletRequest request, String username, String password, String remenberMe, HttpServletResponse response) {

        HttpSession session = request.getSession();

        if(username == null || password == null){
            return "login";
        }else if("".equals(username)){
            request.setAttribute("loginErrorTips", "用户名不存在");
            return "login";
        }
        else if(session.getAttribute("username") != null){/*session中有用户时直接跳转*/
            return "redirect:/personIndex";
        } else {
            User user = userDAO.selectUserByName(username);

            if(user == null){
                request.setAttribute("loginErrorTips", "用户不存在");
                return "login";
            }else if (password.equals(user.getPassword())) {
                session.setAttribute("userId", user.getId().toString());
                session.setAttribute("userInfo",user);
                /*开启免登录后记录用户id至cookie*/
                if ("on".equals(remenberMe)) {
                    Cookie userIdCookie = new Cookie("userId", user.getId().toString());
                    userIdCookie.setMaxAge(60 * 60 * 24);
                    userIdCookie.setPath(request.getContextPath());
                    response.addCookie(userIdCookie);
                }
                return "redirect:/personIndex";
            } else {
                request.setAttribute("loginErrorTips", "密码错误");
                return "login";
            }
        }
    }

    @Override
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置不缓存图片
        System.out.println("new image");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","No-cache");
        response.setDateHeader("Expires",0);
        //指定生成的响应图片
        response.setContentType("image/jpeg");
        Object[] objects = ImageUtil.createImage();
        BufferedImage image = (BufferedImage) objects[1];
        HttpSession session = request.getSession(true);
        //存储验证码数据到Session中
        session.setAttribute("IdentifyCode",objects[0]);
        System.out.println(session.getAttribute("IdentifyCode"));
        //image.dispose();
        //将图形验证码IO流传输至前端
        ImageIO.write(image,"JPEG",response.getOutputStream());
    }

    /**
     * 删除session中的用户及cookie中的用户
     * @param userId
     * @param request
     * @param response
     * @return
     */
    @Override
    public String userLoginOut(String userId, HttpServletRequest request, HttpServletResponse response) {
        if(userId!=null && userId.equals(request.getSession().getAttribute("userId"))){
            request.getSession().removeAttribute("userId");
            Cookie[] cookies = request.getCookies();
            for(int i=0;i<cookies.length;i++){
                if(cookies[i].getName().equals("userId")&&cookies[i].getValue().equals(userId)){
                    cookies[i].setMaxAge(0);
                    cookies[i].setPath(request.getContextPath());
                    response.addCookie(cookies[i]);
                }
            }
        }
        return "redirect:/hello";
    }

    @Override
    public int insertUserSelective(User user){
        if(user.getId()!=null || userDAO.selectUserByName(user.getName())!=null){
            return 0;
        }
        if(user.getLevel()==null){
            user.setLevel(0);
        }
        if(user.getFansNumber()==null){
            user.setFansNumber(0);
        }
        if(user.getFollowsNumber()==null){
            user.setFollowsNumber(0);
        }
        user.setCreateTime(new Date(System.currentTimeMillis()));
        user.setLastLoginin(new Date(System.currentTimeMillis()));
        int i = userDAO.insertUser(user);
        return i;
    }

    /**
     * 根据用户邮箱发送邮件进行验证
     * @param email
     * @param session
     * @return
     * @throws MessagingException
     */
    @Override
    public String getEmailCheckResp(String email,HttpSession session){
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<4;i++){
            buffer.append(((Integer)random.nextInt(9)).toString());
        }
        session.setAttribute("emailCheckCode",buffer.toString());
        //System.out.println(buffer.toString());
        MailUtil mailUtil = new MailUtil();
        try {
            mailUtil.sendMail(email,"您的验证码是：   "+buffer.toString());
        } catch (MessagingException e) {
            e.printStackTrace();
            return "false";
        }

        return "true";
    }

    @Override
    public String getEmailCheckValResp(String emailCheckCode, HttpSession session) {
        String code = (String) session.getAttribute("emailCheckCode");
        if(code != null && code.equalsIgnoreCase(emailCheckCode)){
            return "true";
        }
        return "false";
    }

    @Override
    public String putUserInfoToSession(HttpSession session) {
        int userId = Integer.parseInt(String.valueOf(session.getAttribute("userId")));
        User user = userDAO.selectUserById(userId);
        session.setAttribute("userInfo",user);
        //System.out.println(user);
        return "true";
    }

    @Override
    public int updateUserOptional(User user, HttpSession session) {
        int result = userDAO.modifyUser(user);
        if(result >= 1){
            putUserInfoToSession(session);
        }
        return result;
    }

    /*检测关注状态*/
    @Override
    public String checkFollowStatus(Integer userupId, Integer userfollowsId) {
        Integer i = userDAO.checkFollowStatus(userupId, userfollowsId);
        if(i!=null){
            return "Followed";
        }
        return "NoFollow";
    }

    @Override
    public String changeFollowedStatus(Integer userupId, Integer userfollowsId) {
        Integer i = userDAO.checkFollowStatus(userupId, userfollowsId);
        if(i==null){
            UserToUser userToUser = new UserToUser();
            userToUser.setUserupId(userupId);
            userToUser.setUserfollowsId(userfollowsId);
            int i1 = userDAO.insertUsertoUser(userToUser);
            return "Followed";
        }else{
            int i1 = userDAO.deleteUsertoUserById(i);
            return "NoFollow";
        }
    }

    @Override
    public String getAllUsers(Model model, Integer page, Integer pageSize) {
        /*        PageHelper.startPage(page,pageSize);
        List<Video> videos = videoDAO.getVideoInfoByUserId(userId);
        PageInfo<Video> pageInfo = new PageInfo<>(videos);
        Gson gson = new Gson();
        String json = gson.toJson(pageInfo);
        return json;*/
        PageHelper.startPage(page,pageSize);
        List<User> users = userDAO.getAllUsers();
        model.addAttribute("users",users);
        return null;
    }
}
