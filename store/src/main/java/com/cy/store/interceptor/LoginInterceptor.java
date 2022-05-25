package com.cy.store.interceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个拦截器
 */
public class LoginInterceptor implements HandlerInterceptor{
    /**
     * 检查全局对象中是否有全局uid数据，如果有则放行，没有则重定向到登录界面
     * @param request 请求对象
     * @param response 请求响应
     * @param handler 处理器（url+controller:映射）
     * @return 如果返回值真 表示放行当前的请求 付过返回值为假 表示拦截当前的请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object uid = request.getSession().getAttribute("uid");
        if (uid==null){//用户没有登录过这个系统 重定向到登录界面
            response.sendRedirect("/web/login.html");
            //结束后续的调用
            return  false;
        }
          //请求放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
