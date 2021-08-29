package com.xyz.mqtt.service.impl;

import com.xyz.mqtt.config.MqttProperties;
import com.xyz.mqtt.service.MqttProducer;
import com.xyz.mqtt.util.MqttConnection;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MQTT生产者实现类
 */
@Service
public class MqttProducerImpl implements MqttProducer {
    /**
     * 注入MQTT的配置属性
     */
    @Resource
    private MqttProperties mqttProperties;

    /**
     * MQTT发送消息
     *
     * @param subTopic 子主题
     * @param context  消息内容
     */
    @Override
    public Boolean sendMessage(String subTopic, String context) throws InvalidKeyException, NoSuchAlgorithmException, MqttException {
        // 创建MQTT连接对象
        MqttConnection mqttConnection = new MqttConnection(mqttProperties);
        // 创建MQTT客户端对象
        MqttClient mqttClient = new MqttClient(mqttProperties.getEndPoint(), mqttProperties.getClientId(), new MemoryPersistence());
        // 客户端设置好发送超时时间，防止无限阻塞
        mqttClient.setTimeToWait(5000);
        // MQTT客户端发送消息的回调
        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                System.out.println("1. 生产者MQTT客户端连接MQTT服务器完成");
            }

            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println("1. 生产者MQTT客户端连接MQTT服务器失败");
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println("2. 生产者MQTT客户端发送消息到MQTT服务器完成");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                System.out.println("3. 生产者MQTT客户端投递消息到MQTT服务器完成");
            }
        });
        // MQTT设置连接
        mqttClient.connect(mqttConnection.getMqttConnectOptions());
        // 创建消息体对象
        MqttMessage message = new MqttMessage(context.getBytes(StandardCharsets.UTF_8));
        // 设置消息服务质量
        message.setQos(mqttProperties.getQosLevel());
        // 指定消息主题topic = 父主题 + / + 子主题
        String topic = mqttProperties.getParentTopic() + "/" + subTopic;
        // MQTT客户端发送消息
        mqttClient.publish(topic, message);
        return true;
    }
}
