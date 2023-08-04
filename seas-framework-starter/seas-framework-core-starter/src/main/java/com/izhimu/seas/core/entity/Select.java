package com.izhimu.seas.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 选择器数据
 *
 * @author haoran
 * @version v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Select<T extends Serializable> implements Serializable {

    private String label;

    private T value;
}
