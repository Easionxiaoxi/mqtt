package com.xyz.mqtt.config;

import com.xyz.mqtt.util.MqttConnection;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * MQTT消费者
 */
@Configuration
public class MqttConsumerConfig {
    /**
     * 注入MQTT的配置属性
     */
    @Resource
    private MqttProperties mqttProperties;
    // 订阅的消息主题
    private final String subTopic = "/1001";

    @Bean
    public MqttClient buildMqttClient() throws InvalidKeyException, NoSuchAlgorithmException, MqttException {
        // 创建MQTT连接对象
        MqttConnection mqttConnection = new MqttConnection(mqttProperties);
        // 创建MQTT客户端对象
        MqttClient mqttClient = new MqttClient(mqttProperties.getEndPoint(), mqttProperties.getClientId(), new MemoryPersistence());
        // 客户端设置好发送超时时间，防止无限阻塞
        mqttClient.setTimeToWait(5000);
        // 创建消费者线程池
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        // 监听消息的回调
        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                System.out.println("1. MQTT客户端连接MQTT服务器完成");
                // 连接完成立即订阅消息
                executorService.submit(() -> {
                    try {
                        String topicFilter[] = {mqttProperties.getConsumerTopic()+subTopic};
                        int[] qos = {mqttProperties.getQosLevel()};
                        mqttClient.subscribe(topicFilter, qos);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println("1. MQTT客户端连接MQTT服务器失败");
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.printf("2. MQTT客户端接收到MQTT服务器消息，消息内容是: %s%n",new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                System.out.println("3. MQTT客户端接收MQTT服务器消息完成");
            }
        });
        // MQTT设置连接
        mqttClient.connect(mqttConnection.getMqttConnectOptions());
        return mqttClient;
    }
}
