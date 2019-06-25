package com.example.emq.service;

/**
 * @author Jorden
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/4/1816:46
 */
public interface EmqService {
    Boolean publish(String topic,String content);

    Boolean subscribe(String topic);
}
