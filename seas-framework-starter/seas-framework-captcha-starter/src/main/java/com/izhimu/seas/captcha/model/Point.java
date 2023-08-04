package com.izhimu.seas.captcha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 坐标实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point implements Serializable {

    private int x;

    private int y;

    private String key;

    private String publicKey;
}
