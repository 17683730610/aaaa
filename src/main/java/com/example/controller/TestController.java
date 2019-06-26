package com.example.controller;

import com.example.demo.Result;
import com.example.pojo.Job;
import com.example.pojo.User;
import com.example.service.TestService;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lijinlong
 * @version 1.0
 * @date 2019/6/11 11:37
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping("/login")
    public String userLogin(HttpServletRequest request) {
        String responseUrl = testService.userLogin(request);
        if (responseUrl != null) {
            return "redirect:" + responseUrl;
        }
        return "redirect:/";
    }

    @PostMapping("/getCode")
    public Object getCode(HttpServletRequest request) {

        String responseUrl = testService.getCode(request);
        if (responseUrl != null) {
            return "redirect:" + responseUrl;
        }
        return "redirect:/";
    }

    @PostMapping("/accessToken")
    public HttpEntity getToken(HttpServletRequest request){

        return testService.accessToken(request);
    }

    @PostMapping("/action/{name}")
    public Result action(@PathVariable String name) {
        List<Job> job = testService.findJob(name);
        return new Result(job);
    }

}
