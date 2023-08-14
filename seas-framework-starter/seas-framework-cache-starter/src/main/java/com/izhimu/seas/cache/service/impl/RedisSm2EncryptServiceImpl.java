package com.izhimu.seas.cache.service.impl;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.enums.EncryptType;
import com.izhimu.seas.cache.exception.EncryptException;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.cache.service.RedisService;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;


/**
 * 基于Redis实现的SM2加密服务
 *
 * @author haoran
 * @version v1.0
 */
@Service
@ConditionalOnClass(value = RedisService.class)
@ConditionalOnProperty(prefix = "seas.security", name = "encrypt-mode", havingValue = "SM2", matchIfMissing = true)
public class RedisSm2EncryptServiceImpl implements EncryptService<EncryptKey, String> {

    @Resource
    private RedisService redisService;

    @Override
    public EncryptKey createEncryptKey(long timeout) {
        String key = IdUtil.nanoId();

        SM2 sm2 = SmUtil.sm2();
        String privateKey = HexUtil.encodeHexStr(BCUtil.encodeECPrivateKey(sm2.getPrivateKey()));
        String publicKey = HexUtil.encodeHexStr(((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false));

        EncryptKey encryptKey = new EncryptKey(key, EncryptType.SM2, publicKey, privateKey, Instant.now().toEpochMilli());
        redisService.set(cacheKey(key), encryptKey, timeout);
        return encryptKey;
    }

    @Override
    public EncryptKey getEncryptKey(String key) {
        return redisService.get(cacheKey(key), EncryptKey.class);
    }

    @Override
    public boolean delEncryptKey(String key) {
        return redisService.del(cacheKey(key));
    }

    @Override
    public String encrypt(String key, String obj) throws EncryptException {
        try {
            EncryptKey encryptKey = getEncryptKey(key);
            final SM2 sm2 = SmUtil.sm2(encryptKey.getPrivateKey(), encryptKey.getPublicKey());
            return sm2.encryptHex(obj, KeyType.PublicKey);
        } catch (Exception e) {
            throw new EncryptException("Redis SM2 加密异常");
        }
    }

    @Override
    public String decrypt(String key, String obj) throws EncryptException {
        try {
            EncryptKey encryptKey = getEncryptKey(key);
            final SM2 sm2 = SmUtil.sm2(encryptKey.getPrivateKey(), encryptKey.getPublicKey());
            return sm2.decryptStr(obj, KeyType.PrivateKey);
        } catch (Exception e) {
            throw new EncryptException("Redis SM2 解密异常");
        }
    }

    @Override
    public String sign(String key, String content) throws EncryptException {
        try {
            EncryptKey encryptKey = getEncryptKey(key);
            final SM2 sm2 = SmUtil.sm2(encryptKey.getPrivateKey(), encryptKey.getPublicKey());
            return sm2.signHex(content);
        } catch (Exception e) {
            throw new EncryptException("Redis SM2 签名异常");
        }
    }

    @Override
    public boolean verify(String key, String sign, String reference) throws EncryptException {
        try {
            EncryptKey encryptKey = getEncryptKey(key);
            final SM2 sm2 = SmUtil.sm2(encryptKey.getPrivateKey(), encryptKey.getPublicKey());
            return sm2.verifyHex(sign, reference);
        } catch (Exception e) {
            throw new EncryptException("Redis SM2 签名异常");
        }
    }
}
