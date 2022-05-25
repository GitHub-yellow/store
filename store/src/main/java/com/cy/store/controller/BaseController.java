package com.cy.store.controller;

import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.PasswordNotMatchException;
import com.cy.store.service.ex.UserNotFoundException;
import com.cy.store.service.ex.UsernameDuplicateException;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

/**
 * 控制层的基类
 */
public class BaseController {
    /**
     * 成功状态码
     */
    public static  final int OK=200;
    //统一处理抛出异常  这个方法返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    //当项目产生异常将被统一拦截到此方法，该方法充当请求处理方法，方法返回值直接给前端
    @ExceptionHandler(SerialException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof UsernameDuplicateException){
            result.setState(4000);
            result.setMessage("该账户已被占用");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("注册是产生未知异常");
        }else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("用户数据不存在的异常");
        }else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("用户密码错误的异常");
        }else if (e instanceof PasswordNotMatchException) {
            result.setState(5003);
            result.setMessage("用户跟新信息时产生错误的异常");
        }
           return  result;
    }

    /**
     * 获取session对象中的uid
     * @param session
     * @return 当前登录用户的UID
     */
    protected final Integer getUidFromSession(HttpSession session){
      return  Integer.valueOf(session.getAttribute("uid").toString());
    }
    /**
     * 获取session对象中的username
     * @param session
     * @return 当前登录用户的username
     */
    protected final String getUsernameFromSession(HttpSession session){
        return  String.valueOf(session.getAttribute("username").toString());
    }

}
