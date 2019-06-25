package com.example.emq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jorden
 * @Description: emq配置类
 * @date 2018/4/18 14:19
 */
@Component // 不加这个注解的话, 使用@Autowired 就不能注入进去了
@ConfigurationProperties(prefix = "mqtt") // 配置文件中的前缀
@Data
public class MqttConfiguration {
    private String host;
    private String username;
    private String password;
    private Integer qos;
    private String[] hosts;
    private Integer connectionTimeout;
    private Integer keepAliveInterval;
    private String publishClientId;
    private String subscribeClientId;
}
