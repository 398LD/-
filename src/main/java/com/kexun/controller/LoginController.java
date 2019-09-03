package com.kexun.controller;

import com.kexun.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {


    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@CookieValue(value = "token", required = false) Cookie cookie) {

        if (cookie != null) {
            //如果cookie不为空则说明已经d登陆 则直接跳转主界面
            return "redirect:/index";
        }
        return "login";
    }

    @RequestMapping("index")
    public String index() {

        return "index";

    }

    //处理登陆
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(User user, HttpServletResponse response, Model model) {

//        验证用户 模拟登陆
        if (user != null) {
            //判断用户名密码是否正确
            if ("lidong".equals(user.getUname()) && "123".equals(user.getUpassword())) {

                //生成token
                String token = UUID.randomUUID().toString();

                //创建cookie
                Cookie cookie = new Cookie("token", token);

                //设置域名
                cookie.setDomain("kexun.com");

                //写入到redis
                redisTemplate.opsForValue().set(token, user);

                //将cookie响应给浏览器
                response.addCookie(cookie);

                return "redirect:/index";

            } else {
                model.addAttribute("msg", "账号或密码错误");
                return "login";
            }

        } else {
            model.addAttribute("msg", "账号或密码错误");
            return "login";
        }


    }

    //login out
    @RequestMapping("loginout")
    public String loginout(@CookieValue(value = "token") Cookie cookie, HttpServletResponse response) {

        //设置域
        cookie.setDomain("kexun.com");
        //设置过期时间
        cookie.setMaxAge(0);
        //拿到token
        String token = cookie.getValue();
        //根据tokenc从redis删除
        redisTemplate.delete(token);
        //响应token到浏览器
        response.addCookie(cookie);
        //返回到登录界面
        return "redirect:/login";

    }


}
