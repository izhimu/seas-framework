package com.izhimu.seas.mqtt.constant;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * MQTT常量
 *
 * @author haoran
 */
@UtilityClass
public class MQTTConst {

    public static final Pattern TOPIC_NAME_PATTERN = Pattern.compile("^[^$#+\\u0000]+$");
    public static final Pattern TOPIC_RULE_PATTERN = Pattern.compile("^(#|((\\+(?![^/]))?([^#+]*(/\\+(?![^/]))?)*(/#)?))$");

    public static final String SINGLE_LAYER_SYMBOL = "+";
    public static final String MULTI_LAYER_SYMBOL = "#";
    public static final String SINGLE_LAYER_PATTERN = "[\\w/]*";
    public static final String MULTI_LAYER_PATTERN = "[\\w]*";
}
