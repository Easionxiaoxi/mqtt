package com.xyz.mqtt.service;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MQTT消息生产者
 */
public interface MqttProducer {
    /**
     * MQTT发送消息
     * @param subTopic 子主题
     * @param context 消息内容
     */
    Boolean sendMessage(String subTopic, String context) throws InvalidKeyException, NoSuchAlgorithmException, MqttException;
}
