package com.cy.store.mapper;


import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.Date;

//@SpringBootTest标注当前类是一个测试类 不会随项目一块打包
@SpringBootTest
//@RunWith(SpringRunner.class) 启动单元测试类
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired(required=true)
   private UserMapper userMapper;
    /**
     * 单元测试 必须是被test注解修饰，返回类型viod 访问修饰是public  方法参数列表不能有参数
     * 满足以上条件 才能够独立运行
     */
 @Test
    public  void  insert(){
      User user=new User();
      user.setUsername("tim");
      user.setPassword("123");
     Integer crows=userMapper.insert(user);
     System.out.println(crows);

    }
    @Test
    public void findByUsername(){
     User user=userMapper.findByUsername("tim");
        System.out.println(user);
    }
    @Test
    public void findByUid(){
        User user = userMapper.findByUid(2);
        System.out.println(user);

    }
    @Test
    public void updatePasswordByUid(){
        Date date=new Date();
        Integer b = userMapper.updatePasswordByUid(3, "456", "ttt", date);
        if (b!=null){
            System.out.println("修改成功");
        }else{
            System.out.println("error");
        }

    }
    @Test
    public void updateInfoByUid(){
      User user=new User();
      user.setUid(1);
      user.setPhone("123456789");
      user.setEmail("1234678@qq.com");
      user.setGender(1);
      user.setModifiedUser("tim");
      user.setModifiedTime(new Date());
        Integer uid = userMapper.updateInfoByUid(user);
        if (uid!=null){
            System.out.println("修改成功");
        }else {
            System.out.println("修改失败");
        }
    }

}
