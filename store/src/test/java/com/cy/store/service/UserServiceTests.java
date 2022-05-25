package com.cy.store.service;


import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//@SpringBootTest标注当前类是一个测试类 不会随项目一块打包
@SpringBootTest
//@RunWith(SpringRunner.class) 启动单元测试类
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired(required=true)
   private IUserService userService;
    /**
     * 单元测试 必须是被test注解修饰，返回类型viod 访问修饰是public  方法参数列表不能有参数
     * 满足以上条件 才能够独立运行
     */
 @Test
    public  void  insert(){
     try {
         User user=new User();
         user.setUsername("aa");
         user.setPassword("123456");
         userService.reg(user);
     } catch (ServiceException e) {
         System.out.println(e.getClass().getSimpleName());
         System.out.println(e.getMessage());
         System.out.println("注册失败");

     }
     System.out.println("ok");
    }
    @Test
public void login(){
        User u = userService.login("BB", "123");
        System.out.println(u);
    }
    @Test
    public void changePassword(){
     userService.changePassword(20,"test001","123","456");
        System.out.println("更新成功");
    }
    @Test
    public void  changeInfo(){
        User user=new User();
        user.setPhone("12347549645");
        user.setEmail("156123@qq.com");
        user.setGender(0);
     userService.changInfo(20,"ttt003",user);
        System.out.println("更新信息成功");
    }
    @Test
    public void getByUid(){
        User user = userService.getByUid(20);
        System.out.println(user);
    }
}
