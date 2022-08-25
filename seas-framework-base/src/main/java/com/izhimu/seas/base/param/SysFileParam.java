package com.izhimu.seas.base.param;

import com.izhimu.seas.mybatis.annotation.OrderBy;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件信息参数
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysFileParam implements Serializable {

    /**
     * 主键
     */
    @OrderBy(asc = false)
    private Long id;
}
