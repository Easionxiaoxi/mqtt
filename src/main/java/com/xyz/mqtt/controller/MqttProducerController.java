package com.xyz.mqtt.controller;

import com.xyz.mqtt.domain.MqttMsg;
import com.xyz.mqtt.service.MqttProducer;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MQTT客户端生产者控制器
 */
@RestController
@RequestMapping("/producer")
public class MqttProducerController {

    @Resource
    private MqttProducer mqttProducer;

    /**
     * MQTT发送消息
     *
     * @param mqttMsg 消息子主题和内容
     */
    @PostMapping("/sendMessage")
    Boolean sendMessage(@RequestBody MqttMsg mqttMsg) throws NoSuchAlgorithmException, InvalidKeyException, MqttException {
        return mqttProducer.sendMessage(mqttMsg.getSubTopic(), mqttMsg.getContext());
    }
}
