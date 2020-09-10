package com.fakebilibili.controller;

import com.fakebilibili.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;

@Controller
public class UserController {

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
        HttpSession session = request.getSession();


        if(session.getAttribute("username") != null){
            return "redirect:/personIndex";
        }
        else if(username == null || password == null){
            return "login";
        }
        else if(username.equals("zhangsan")||"".equals(username)){
            request.setAttribute("loginErrorTips","用户名不存在");
        }
        else if(password.equals(username)){
            session.setAttribute("username",username);
            if("on".equals(remenberMe)) {
                Cookie nameCookie = new Cookie("username", username);
                nameCookie.setMaxAge(60*60*24);
                nameCookie.setPath(request.getContextPath());
                Cookie passwordCookie = new Cookie("password", password);
                passwordCookie.setMaxAge(60*60*24);
                passwordCookie.setPath(request.getContextPath());
                response.addCookie(nameCookie);
                response.addCookie(passwordCookie);
            }
            return "redirect:/personIndex";
        }
        else {
            request.setAttribute("loginErrorTips","密码错误");
        }
        return "login";
    }

    @RequestMapping("/login/refreshCheckCode")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response)throws Exception{
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
     * 用户退出,需要同时移除session中的用户
     * @return
     */
    @RequestMapping("/loginOut/{username}")
    public String userLoginOut(@PathVariable("username")String username,HttpServletRequest request,HttpServletResponse response){
        if(username!=null && username.equals(request.getSession().getAttribute("username"))){
            request.getSession().removeAttribute("username");
            Cookie[] cookies = request.getCookies();
            for(int i=0;i<cookies.length;i++){
                if(cookies[i].getName().equals("username")&&cookies[i].getValue().equals(username)){
                    //Cookie k1 = new Cookie(cookies[i].getName(),null);
                    cookies[i].setMaxAge(0);
                    cookies[i].setPath(request.getContextPath());
                    response.addCookie(cookies[i]);
                }else if(cookies[i].getName().equals("password")){
                    cookies[i].setMaxAge(0);
                    cookies[i].setPath(request.getContextPath());
                    response.addCookie(cookies[i]);
                }
            }
        }
        return "redirect:/hello";
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
}
