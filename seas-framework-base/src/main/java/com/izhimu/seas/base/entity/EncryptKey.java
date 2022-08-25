package com.izhimu.seas.base.entity;

import com.izhimu.seas.base.enums.EncryptType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 密钥对象
 *
 * @author haoran
 * @version v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptKey implements Serializable {

    /**
     * 密钥键
     */
    private String key;

    /**
     * 密钥类型
     */
    private EncryptType type;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 密钥生成时间
     */
    private Long timestamp;

    /**
     * 转换为视图数据
     *
     * @return 密钥对象
     */
    public EncryptKey toView() {
        return new EncryptKey(this.key, null, this.publicKey, null, this.timestamp);
    }
}
