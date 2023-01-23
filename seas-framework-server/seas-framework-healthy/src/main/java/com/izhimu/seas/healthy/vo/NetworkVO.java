package com.izhimu.seas.healthy.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 网络信息视图
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class NetworkVO implements Serializable {

    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 状态
     */
    private String status;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * IPV4地址
     */
    private String ipv4;

    /**
     * IPV6地址
     */
    private String ipv6;

    /**
     * mtu
     */
    private Long mtu;

    /**
     * 速度
     * /100000 Mbps
     */
    private Integer speed;

    /**
     * 接收字节数
     */
    private String bytesRecv;

    /**
     * 发送字节数
     */
    private String bytesSent;

    /**
     * 接收包数
     */
    private String packetsRecv;

    /**
     * 发送包数
     */
    private String packetsSent;
}
