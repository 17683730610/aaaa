package com.example.service;

import com.example.pojo.Job;
import org.springframework.http.HttpEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lijinlong
 * @version 1.0
 * @date 2019/6/11 18:31
 */
public interface TestService {

    /**
     * 用户登录
     * @param request
     * @return
     */
    String userLogin(HttpServletRequest request);

    /**
     * test
     * @return
     * @param name
     */
    List<Job> findJob(String name);

    /**
     *  获取code
     * @param request
     * @return
     */
    String getCode(HttpServletRequest request);

    /**
     * AccessToken
     * @param request
     * @return
     */
    HttpEntity accessToken(HttpServletRequest request);
}
