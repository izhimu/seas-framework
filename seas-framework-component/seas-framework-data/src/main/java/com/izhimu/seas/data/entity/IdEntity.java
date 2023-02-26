package com.izhimu.seas.data.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 包含主键的实体
 *
 * @author haoran
 * @version 1.0
 */
@Data
public class IdEntity implements Serializable {

    /**
     * ID
     */
    @TableId
    private Long id;
}
