package com.kexun.interceptor;

import com.kexun.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
* TODO 这是一个登录拦截器
* */
public class LoginInterceptor implements HandlerInterceptor {

    private RedisTemplate<String, User> redisTemplate;

    //实例化时初始化redisTemplate
    public LoginInterceptor(RedisTemplate<String, User> redisTemplate) {

        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session 模拟本地session登录
        HttpSession session = request.getSession();
        String token = null;
        Cookie[] cookies = request.getCookies();
        Cookie respcookie = null;
        //没有cookie说明没有登录
        if (cookies == null) {
            response.sendRedirect("/login");
            return false;
        }
        for (Cookie cookie : cookies) {
            //拿到cookie里面的token
            if ("token".equals(cookie.getName())) {
                respcookie = cookie;
                token = cookie.getValue();
            }

        }
        //toke为null说明没有登录
        if (token != null) {
            //根据token从redis获取到用户
            User user = redisTemplate.opsForValue().get(token);
            //已经登录
            if (user != null) {
                //设置个session方便取值
                session.setAttribute("user", user);
                return true;
            } else {
                //token 在redis中没有匹配的key 说明没有登录
                respcookie.setMaxAge(0);
                respcookie.setDomain("kexun.com");
                //响应到浏览器
                response.addCookie(respcookie);
                //回登录界面
                response.sendRedirect("/login");
                return false;
            }
        } else {

            response.sendRedirect("/login");
            return false;
        }


    }
}
