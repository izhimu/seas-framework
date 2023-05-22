package com.izhimu.seas.cache.service;

import com.izhimu.seas.cache.exception.EncryptException;

/**
 * 加密服务
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
public interface EncryptService<E, K> {

    default String cacheKey(String key) {
        return "seas:encrypt:".concat(key);
    }

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
     * 删除密钥对
     *
     * @param key 密钥对标识
     * @return boolean
     */
    boolean delEncryptKey(K key);

    /**
     * 加密
     *
     * @param key 密钥标识
     * @param obj 被加密对象
     * @return 加密后值
     * @throws EncryptException 加密异常
     */
    String encrypt(K key, String obj) throws EncryptException;

    /**
     * 解密
     *
     * @param key 密钥标识
     * @param obj 被解密对象
     * @return 解密后值
     * @throws EncryptException 解密异常
     */
    String decrypt(K key, String obj) throws EncryptException;

    /**
     * 签名
     *
     * @param key     密钥标识
     * @param content 签名对象
     * @return 签名后值
     * @throws EncryptException 签名异常
     */
    default String sign(K key, String content) throws EncryptException {
        return null;
    }

    /**
     * 验签
     *
     * @param key       密钥标识
     * @param sign      签名
     * @param reference 签名参考
     * @return boolean
     * @throws EncryptException 验签异常
     */
    default boolean verify(K key, String sign, String reference) throws EncryptException {
        return false;
    }

}
