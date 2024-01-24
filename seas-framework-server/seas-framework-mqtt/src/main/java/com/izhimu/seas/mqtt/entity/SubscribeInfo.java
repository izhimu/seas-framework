package com.izhimu.seas.mqtt.entity;

import io.netty.handler.codec.mqtt.MqttQoS;

import java.io.Serializable;
import java.util.Objects;

/**
 * 订阅信息
 *
 * @author haoran
 */
public record SubscribeInfo(String clientId, MqttQoS qos) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribeInfo that = (SubscribeInfo) o;
        return Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
