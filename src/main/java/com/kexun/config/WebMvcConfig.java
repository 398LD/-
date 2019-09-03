package com.kexun.config;

import com.kexun.entity.User;
import com.kexun.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /*在拦截器无法实现自动装配 在这可以*/
    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册一个拦截器将redisTemplate也初始化 拦截所有请求但排除/login /login包括get和post请求
        registry.addInterceptor(new LoginInterceptor(redisTemplate)).addPathPatterns("/*").excludePathPatterns("/login");
    }
}
