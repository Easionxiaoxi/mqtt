package com.xyz.mqtt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云MQTT属性配置
 */
@ConfigurationProperties(value = "aliyun.mqtt")
@Configuration
public class MqttProperties {
    private String accessKey;
    private String secretKey;
    private String groupId;
    private String clientId;
    private String parentTopic;
    private String consumerTopic;
    private String endPoint;
    private String mqttInstanceId;
    private String regionId;
    private int qosLevel;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getParentTopic() {
        return parentTopic;
    }

    public void setParentTopic(String parentTopic) {
        this.parentTopic = parentTopic;
    }

    public String getConsumerTopic() {
        return consumerTopic;
    }

    public void setConsumerTopic(String consumerTopic) {
        this.consumerTopic = consumerTopic;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getMqttInstanceId() {
        return mqttInstanceId;
    }

    public void setMqttInstanceId(String mqttInstanceId) {
        this.mqttInstanceId = mqttInstanceId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public int getQosLevel() {
        return qosLevel;
    }

    public void setQosLevel(int qosLevel) {
        this.qosLevel = qosLevel;
    }
}
