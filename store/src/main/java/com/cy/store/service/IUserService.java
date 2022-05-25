package com.cy.store.service;

import com.cy.store.entity.User;

/**
 * 用户模块业务层接口
 */
public interface IUserService {
    /**
     * 用户注册方法
     * @param user
     */
    void reg(User user);

    /**
     * 用户登录抽象方法
     * @param username 用户名
     * @param password 密码
     * @return 返回User对象
     */
    User login(String username, String password);

    /**
     * 修改用户密码
     * @param uid UID
     * @param username 用户名
     * @param oldPassword 数据库中的密码
     * @param newPassword 修改后的密码
     */
    void changePassword(Integer uid,String username, String oldPassword,String newPassword);

    /**
     *
     * @param uid
     * @return
     */
   User getByUid(Integer uid);

    /**
     *更新用户的数据
     * @param uid
     * @param username
     * @param user
     */
   void  changInfo(Integer uid,String username,User user);

}
