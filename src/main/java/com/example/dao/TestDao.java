package com.example.dao;

import com.example.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lijinlong
 * @version 1.0
 * @date 2019/6/12 14:21
 */
public interface TestDao extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {

    /**
     * 查询用户 检验用户名
     * @param username
     * @return
     */
   User findUserByName(String username);

    /**
     * 检验密码
     * @param password
     * @return
     */
    User findUserByPassword(String password);

}
