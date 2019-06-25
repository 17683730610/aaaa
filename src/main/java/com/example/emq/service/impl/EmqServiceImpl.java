package com.example.emq.service.impl;

import com.example.emq.config.MqttConfiguration;
import com.example.emq.config.PublishConn;
import com.example.emq.config.SubscribeConn;
import com.example.emq.service.EmqService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author Jorden
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/4/1816:48
 */
@Service
@Slf4j
@EnableConfigurationProperties({MqttConfiguration.class})
public class EmqServiceImpl implements EmqService {
    @Autowired
    private MqttConfiguration mqttConfiguration;
    @Autowired
    private PublishConn publishConn;
    @Autowired
    private SubscribeConn subscribeConn;

    @Override
    public Boolean publish(String topic, String content) {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(mqttConfiguration.getQos());
        message.setRetained(true);
        MqttClient mqttClient = publishConn.getMqttClient();
        try {
            String clientId = "send";
            // 判定是否需要重新连接
            if (mqttClient==null || !mqttClient.isConnected() || !mqttClient.getClientId().equals(clientId)) {
                mqttClient = publishConn.getConn();
            }
            mqttClient.publish(topic, message);
            log.info("emq已发topic: {} - message: {}", topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        } finally {
            /*//若需要在连接后断开连接，放开注释
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }*/
        }
        return true;
    }

    @Override
    public Boolean subscribe(String topic) {
        MqttClient mqttClient = subscribeConn.getMqttClient();
        String clientId = "receive";
        // 判定是否需要重新连接
        if (mqttClient==null || !mqttClient.isConnected() || !mqttClient.getClientId().equals(clientId)) {
            mqttClient = subscribeConn.getConn(clientId,topic);
        }
        try {
            // 订阅消息
            int[] qos = {mqttConfiguration.getQos()};
            String[] topics = {topic};
            mqttClient.subscribe(topics,qos);
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
