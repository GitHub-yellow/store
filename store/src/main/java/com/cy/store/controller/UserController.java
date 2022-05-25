package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicateException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;



@RestController
@RequestMapping("users")
public class UserController extends  BaseController{
    /**
     * 1.接受数据的方式：请求处理方法的参数列表设置为pojo类型来接收前端数据
     * SpringBoot会将前端URL地址中的参数名会与pojo中的属性名进行比较，如果这两个名称相等，则将值注入到pojo类对应的属性上
     */
    @Autowired
    private IUserService userService;
    @RequestMapping("reg")
//    @ResponseBody //表示响应结果以json格式进行数据响应到前端
    public JsonResult<Void> reg(User user) {
      userService.reg(user);
      return new JsonResult<>(OK);
    }
    /**
     * 2.接受数据的方式：请求处理方法的参数列表设置为非自己封装的pojo类型来接收前端数据
     * SpringBoot会直接将请求的参数名和方法的参数名直接进行比较，如果名称想同，则直接自动完成值的依赖注入
     */
    @RequestMapping("login")
//    @ResponseBody //表示响应结果以json格式进行数据响应到前端
 public JsonResult<User> login(String username, String password, HttpSession session) {
     User data = userService.login(username, password);
     //向session对象中完成数据的绑定（session全局的）
     session.setAttribute("uid",data.getUid());
     session.setAttribute("username",data.getUsername());

     //获取session中绑定的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

     return new JsonResult<User>(OK,data);
 }

    /**
     *
     * @param oldPassword
     * @param newPassword
     */
    @RequestMapping("change_password")
 public  JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
      Integer uid = getUidFromSession(session);
      String username=getUsernameFromSession(session);
      userService.changePassword(uid,username,oldPassword,newPassword);
      return new JsonResult<>(OK);

 }
 @RequestMapping("get_by_uid")
 public JsonResult<User> getByUid(HttpSession session){
     User data = userService.getByUid(getUidFromSession(session));
     return new  JsonResult<>(OK,data);
 }
 @RequestMapping("/change_info")
 public JsonResult<Void> changeInfo(User user,HttpSession session){
       //user对象有4部分数据 phone email username gender
     //uid需要再次封装到user对象中
     Integer uid = getUidFromSession(session);
     String username = getUsernameFromSession(session);
     userService.changInfo(uid,username,user);
     return new JsonResult<>(OK);
 }
//    @RequestMapping("reg")
//    @ResponseBody //表示响应结果以json格式进行数据响应到前端
//    public JsonResult<Void> reg(User user){
//
//        JsonResult<Void> result = new JsonResult<>();
//        try {
//            userService.reg(user);
//            result.setState(200);
//            result.setMessage("注册成功");
//        } catch (UsernameDuplicateException e) {
//            result.setState(4000);
//            result.setMessage("你们用户名被占用了");
//        }catch (InsertException e) {
//            result.setState(5000);
//            result.setMessage("注册时产生未知异常");
//        }
//        return  result;
//    }

}
