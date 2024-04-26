package com.izhimu.seas.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 选择器数据
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class TreeSelect<T extends Serializable> implements Serializable {
    private String label;

    private T value;

    private boolean disabled;

    private List<TreeSelect<T>> children;

    private Map<String, Object> extend;

    public TreeSelect() {
    }

    public TreeSelect(String label, T value) {
        this.label = label;
        this.value = value;
        this.disabled = false;
    }

    public TreeSelect(String label, T value, Map<String, Object> extend) {
        this.label = label;
        this.value = value;
        this.extend = extend;
        this.disabled = false;
    }
}
