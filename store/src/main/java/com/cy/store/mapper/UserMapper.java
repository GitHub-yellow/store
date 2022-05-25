package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块持久层接口
 */

public interface UserMapper {
    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 返回受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询数据
     * @param username  用户名
     * @return 用户数据 否则为 null
     */
    User findByUsername(String username);

    /**
     * /**
     * 通过UID修改用户密码
     *
     * @return 返回值，成功返回1，否则为空
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 通过UID查询用户信息
     * @param uid
     * @return 返回用户所有信息
     */
    User  findByUid(Integer uid);

    /**
     * 根据用户uid来修改用户信息
     * @param user
     * @return 返回值为1表示修改成功，返回值为空表示修改失败
     */
    Integer updateInfoByUid(User user);

}
