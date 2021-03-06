package com.fakebilibili.interceptor;

import com.fakebilibili.controller.ErrorController;
import com.fakebilibili.dao.UserDAO;
import com.fakebilibili.dao.daoimpl.UserDAOImpl;
import com.fakebilibili.entity.User;
import com.fakebilibili.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService service;

    Logger logger = LogManager.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        String userId = null;
        String userIdInSession = (String) session.getAttribute("userId");
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("userId")){
                    userId = cookie.getValue();
                }
            }
        }
        if(userId!=null && userIdInSession==null){
            session.setAttribute("userId",userId);
            service.putUserInfoToSession(session);
        }

        if(userIdInSession!=null||userId!=null){
            logger.info("进入登录拦截器,已登录");
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
