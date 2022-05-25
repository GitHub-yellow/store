package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块实现类
 */
//@Service将当前对象交给spring来管理，自动创建对象并进行维护
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {

        String username=user.getUsername();
        User result=userMapper.findByUsername(username);
        if (result!=null){
            throw new UsernameDuplicateException("用户名被占用");
        }
        //采用md5进行密码加密
        //串+密码+串+加密 连续加载三次
        //盐值+password+盐值----盐值就是一个随机字符串
        String oldPassword=user.getPassword();
        //获取盐值（随机生成一个盐值）
       String salt=UUID.randomUUID().toString().toUpperCase();
        //记录盐值
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密
        String md5password=getMD5Password(oldPassword,salt);
        //将加密后的密码重置
        user.setPassword(md5password);
        //将is_delete字段值设置为0
        user.setIsDelete(0);
        //补全四个日志字段
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date=new Date();
        user.setCreatedTime(date);
        user.getModifiedTime(date);

        Integer rows=userMapper.insert(user);

        if (rows!=1){
            throw new InsertException("在注册过程中产生未知异常");
        }

    }

    @Override
    public User login(String username, String password) {
        //根据用户名称来查询用户是否存在，如果不存在则抛出异常
        User result = userMapper.findByUsername(username);
        if (result==null){
            System.out.println("用户数据不存在");
        }
        //检查用户密码是否匹配，
        //1.获取数据库中加密过后的密码
         String oldPassword=result.getPassword();
        //2.将页面传过来密码按照同样的方法进行加密
        //根据用户名获取对应盐值
        String salt=result.getSalt();
        //加密
        String newMD5Password=getMD5Password(password,salt);
        //3.将两种密码进行比对
        if (!oldPassword.equals(newMD5Password)){
           throw new PasswordNotMatchException("用户密码错误");
        }
        if (result.getIsDelete()==1){
            throw new UserNotFoundException("用户不存在");
        }
        //创建user对象并只封装三个数据，这样可以提升系统的性能
        User user=new User();
        //前面已经查询过来直接调用
        user.setUid(result.getUid());
       user.setUsername(result.getUsername());
       user.setAvatar(result.getAvatar());

        //返回user对象
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户没找到");
        }
        //原始密码和数据库中的比较
        String salt=result.getSalt();
        String oldMD5Password=getMD5Password(oldPassword,salt);
        if (!result.getPassword().equals(oldMD5Password)){
            throw new PasswordNotMatchException("密码不匹配");
        }
        //将新的密码设置到数据库中  将新的密码加密 在保存到数据库中
        String newMD5Password=getMD5Password(newPassword,salt);
        Integer rows = userMapper.updatePasswordByUid(uid, newMD5Password, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新异常");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据存在");
        }
        User user=new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void changInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows!=1){
            throw new UpdateException("数据更新时异常");
        }

    }

    /**
     * 定义一个MD5加密算法
     */
    private String getMD5Password(String password,String salt){
        //三次加密
        for (int i=1;i<=3;i++){
          password= DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
         return  password;
    }
}
