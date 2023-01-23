package com.izhimu.seas.healthy.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 传感器信息视图
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SensorsVO implements Serializable {

    /**
     * CPU温度
     */
    private Double cpuTemperature;

    /**
     * CPU电压
     */
    private Double cpuVoltage;

    /**
     * 风扇转速
     */
    private List<Integer> fanSpeeds;
}
