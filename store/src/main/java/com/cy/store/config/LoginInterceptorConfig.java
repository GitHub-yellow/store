package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


/**
 * 完成处理器拦截器的注册
 */
@Configuration //加载当前拦截器并进行注册
public class LoginInterceptorConfig implements WebMvcConfigurer {

    /**
     * 用来配置拦截器的
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建拦截器对象
        HandlerInterceptor interceptor=new LoginInterceptor();
        //配置白名单 ：存放在一个list集合中
        List<String> patterns = new ArrayList<>();
        patterns.add("/boot/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        //拦截器的注册
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**") //配置要拦截的路径(url)是
                .excludePathPatterns(patterns);

    }
}
