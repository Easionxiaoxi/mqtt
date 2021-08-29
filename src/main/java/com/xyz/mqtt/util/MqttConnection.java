package com.xyz.mqtt.util;

import com.xyz.mqtt.config.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.eclipse.paho.client.mqttv3.MqttConnectOptions.MQTT_VERSION_3_1_1;

/**
 * 工具类：负责封装 MQ4IOT 客户端的初始化参数设置
 */
public class MqttConnection {
    /**
     * 内部连接参数
     */
    private MqttConnectOptions mqttConnectOptions;
    /**
     * 客户端使用的 Token 参数，仅在 Token 鉴权模式下需要设置，Key 为 token 类型，
     * 一个客户端最多存在三种类型，R，W，RW，Value 是 token内容。
     * 应用需要保证 token 在过期及时更新。否则会导致连接异常。
     */
    private Map<String, String> tokenData = new ConcurrentHashMap<String, String>();

    /**
     * Token 鉴权模式下构造方法
     *
     * @param mqttProperties mqtt属性配置
     * @param tokenData      客户端使用的 Token 参数，仅在 Token 鉴权模式下需要设置
     */
    public MqttConnection(MqttProperties mqttProperties, Map<String, String> tokenData) {
        StringBuilder builder = new StringBuilder();
        if (tokenData != null) {
            this.tokenData.putAll(tokenData);
        }
        for (Map.Entry<String, String> entry : tokenData.entrySet()) {
            builder.append(entry.getKey()).append("|").append(entry.getValue()).append("|");
        }
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName("Token|" + mqttProperties.getAccessKey() + "|" + mqttProperties.getMqttInstanceId());
        mqttConnectOptions.setPassword(builder.toString().toCharArray());
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setKeepAliveInterval(90);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setMqttVersion(MQTT_VERSION_3_1_1);
        mqttConnectOptions.setConnectionTimeout(5000);
    }

    /**
     * Signature 鉴权模式下构造方法
     *
     * @param mqttProperties MQTT配置属性
     */
    public MqttConnection(MqttProperties mqttProperties) throws NoSuchAlgorithmException, InvalidKeyException {
        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName("Signature|" + mqttProperties.getAccessKey() + "|" + mqttProperties.getMqttInstanceId());
        mqttConnectOptions.setPassword(Tools.macSignature(mqttProperties.getClientId(), mqttProperties.getSecretKey()).toCharArray());
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setKeepAliveInterval(90);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setMqttVersion(MQTT_VERSION_3_1_1);
        mqttConnectOptions.setConnectionTimeout(5000);
    }

    /**
     * 获取MQTT连接对象
     */
    public MqttConnectOptions getMqttConnectOptions() {
        return mqttConnectOptions;
    }

}
