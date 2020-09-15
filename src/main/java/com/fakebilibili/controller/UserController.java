package com.fakebilibili.controller;

import com.fakebilibili.entity.User;
import com.fakebilibili.service.UserService;
import com.fakebilibili.util.ImageUtil;
import com.sun.xml.internal.ws.api.FeatureListValidatorAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 处理对用户进行操作的请求
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录界面，第一次登录时，跳转至登录界面，当session中已经存在用户时，直接跳转至个人主页。
     * 登录完成后会将用户放入session,不用再次登录
     * @param request
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String userLogin(HttpServletRequest request, @RequestParam(required = false) String username,
                            @RequestParam(required = false) String password, @RequestParam(required = false)String remenberMe
                            , HttpServletResponse response){

        String result = userService.userLogin(request,username,password,remenberMe,response);
        return result;
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/login/refreshCheckCode")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response)throws Exception{

        userService.getCheckCode(request,response);

    }


    @RequestMapping(value = "/checkIdentifyCode",method = RequestMethod.POST)
    @ResponseBody
    public String checkIdentifyCode(@RequestParam(required = false) String checkCode,HttpSession session){
        //System.out.println("ajax"+checkCode);
        String result = "false";
        if(session.getAttribute("IdentifyCode") != null
                && session.getAttribute("IdentifyCode").toString().equalsIgnoreCase(checkCode)){
            result = "true";
        }
        return result;
    }

    /**
     * 用户退出,需要同时移除session中的用户
     * @return
     */
    @RequestMapping("/loginOut/{userId}")
    public String userLoginOut(@PathVariable("userId")String userId,HttpServletRequest request,HttpServletResponse response){

        String result = userService.userLoginOut(userId,request,response);
        return result;
    }

    /**
     * 个人主页，前端页面根据session获取个人信息
     * @return
     */
    @RequestMapping("/personIndex")
    public String jumpToPersonIndex(){
        return "personIndex";
    }

    /**
     * 跳转至用户注册界面
     * @return
     */
    @RequestMapping("/register")
    public String userRegister(){
        return "register";
    }


    @RequestMapping("/validation")
    public String checkInputIno(@ModelAttribute @Valid User user, BindingResult bindingResult,HttpSession session){
        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                session.setAttribute("fieldError",fieldError);
                System.out.println(fieldError.getDefaultMessage());
            }
            return "register";
        }
        session.removeAttribute("fieldError");
        int result = userService.insertUserSelective(user);
        if(result >= 1) {
            return "index";
        }else{
            return "register";
        }
    }

    @RequestMapping(value = "/emailcheckCode",method = RequestMethod.POST)
    @ResponseBody
    public String getEmailCheckResp(@RequestParam("email") String email,HttpSession session){
        return userService.getEmailCheckResp(email,session);
    }


    @RequestMapping(value = "/emailCheckCodeVal",method = RequestMethod.POST)
    @ResponseBody
    public String getEmailCheckValResp(@RequestParam("emailCheckCode") String emailCheckCode,HttpSession session){
        return userService.getEmailCheckValResp(emailCheckCode,session);
    }


    @RequestMapping(value = "/putUserInfo",method = RequestMethod.POST)
    public String putUserInfoToSession(HttpSession session){
        userService.putUserInfoToSession(session);
        return "true";
    }


    @RequestMapping(value = "/userInfoChange/{id}",method = RequestMethod.POST)
    public String jumpToUserUpdate(@PathVariable(value = "id",required = false)Integer id){
        return "userInfoUpdate";
    }


    @RequestMapping(value = "/updateUserOptional",method = RequestMethod.POST)
    public String updateUserOptional(User user,HttpSession session,HttpServletResponse response) throws IOException {
        //System.out.println(user);
        Date lastLoginin = user.getLastLoginin();
        user.setLastLoginin(new java.sql.Date(lastLoginin.getTime()));
        int result = userService.updateUserOptional(user,session);
        if(result >=1 ){
            return "personIndex";
        }else{
            //response.getWriter().write("修改失败了！");
            return "userInfoUpdate";
        }
    }

    @RequestMapping(value = "/changeHeadPic",method = RequestMethod.POST)
    public String changeUserHeadPic(@RequestParam(required = true)MultipartFile headPic,HttpSession session) throws IOException {

        if(headPic != null){
            String headPicPath = "D:\\gitRespository\\FakeBilibili\\uploadFiles\\";

            String orignalPicName = headPic.getOriginalFilename();
            User user = (User) session.getAttribute("userInfo");
            String userHeadPic = user.getHeadPic();
            if(userHeadPic!=null){
                File file  = new File(headPicPath + userHeadPic.substring(8));
                file.delete();
            }
            String userId = user.getId().toString();
            String newFileName = orignalPicName.substring(0,3) + userId + ".jpg";
            File file  = new File(headPicPath + newFileName);
            String mapPath = "/upload/"+newFileName;
            headPic.transferTo(file);
            user.setHeadPic(mapPath);
            userService.updateUserOptional(user,session);
        }

        return "personIndex";
    }


    /*检查关注状态*/
    @RequestMapping(value = "/checkIsFollowed",method = RequestMethod.GET)
    @ResponseBody
    public String checkIsFollowed(@RequestParam("videoAuthorid")Integer userupId,
                                  @RequestParam("thisUserId")Integer userfollowsId){
        System.out.println(userupId+"===="+userfollowsId);
        return userService.checkFollowStatus(userupId,userfollowsId);
    }

    /*切换关注状态*/
    @RequestMapping(value = "/changeFollowedStatus",method = RequestMethod.POST)
    @ResponseBody
    public String changeFollowedStatus(@RequestParam("videoAuthorid")Integer userupId,
                                  @RequestParam("thisUserId")Integer userfollowsId){
        return userService.changeFollowedStatus(userupId,userfollowsId);
    }
}
