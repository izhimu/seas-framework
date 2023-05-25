package com.izhimu.seas.data.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.izhimu.seas.core.web.entity.IView;
import lombok.Data;

import java.io.Serializable;

/**
 * 包含主键的实体
 *
 * @author haoran
 * @version 1.0
 */
@Data
public class IdEntity implements Serializable, IView {

    /**
     * ID
     */
    @TableId
    private Long id;
}
