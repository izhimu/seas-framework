package com.izhimu.seas.base.service;

import com.izhimu.seas.base.exception.EncryptException;

/**
 * 加密服务
 *
 * @author haoran
 * @version v1.0
 */
public interface EncryptService<E, K> {

    /**
     * 生成加密密钥对
     *
     * @param timeout 过期时间（秒）
     * @return 密钥对
     */
    E createEncryptKey(long timeout);

    /**
     * 根据Key获取密钥对
     *
     * @param key 密钥对标识
     * @return 密钥对
     */
    E getEncryptKey(K key);

    /**
     * 加密
     *
     * @param key 密钥对
     * @param obj 被加密对象
     * @return 加密后值
     * @throws EncryptException 加密异常
     */
    String encrypt(E key, String obj) throws EncryptException;

    /**
     * 解密
     *
     * @param key 密钥对
     * @param obj 被解密对象
     * @return 解密后值
     * @throws EncryptException 解密异常
     */
    String decrypt(E key, String obj) throws EncryptException;

}
