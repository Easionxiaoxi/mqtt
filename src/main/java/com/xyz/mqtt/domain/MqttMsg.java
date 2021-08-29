package com.xyz.mqtt.domain;

/**
 * MQTT消息
 */
public class MqttMsg {

    private String subTopic;
    private String context;

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
