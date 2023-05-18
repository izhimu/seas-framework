package com.izhimu.seas.security.service.impl;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.izhimu.seas.cache.service.RedisService;
import com.izhimu.seas.security.entity.EncryptKey;
import com.izhimu.seas.security.enums.EncryptType;
import com.izhimu.seas.security.exception.EncryptException;
import com.izhimu.seas.security.service.EncryptService;
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

    private static final String CACHE_ENCRYPT_KEY = "encrypt:sm2.key.";

    @Resource
    private RedisService redisService;

    @Override
    public EncryptKey createEncryptKey(long timeout) {
        String key = IdUtil.nanoId();

        SM2 sm2 = SmUtil.sm2();
        String privateKey = HexUtil.encodeHexStr(BCUtil.encodeECPrivateKey(sm2.getPrivateKey()));
        String publicKey = HexUtil.encodeHexStr(((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false));

        EncryptKey encryptKey = new EncryptKey(key, EncryptType.SM2, publicKey, privateKey, Instant.now().toEpochMilli());
        redisService.set(CACHE_ENCRYPT_KEY.concat(key), encryptKey, timeout);
        return encryptKey;
    }

    @Override
    public EncryptKey getEncryptKey(String key) {
        EncryptKey encryptKey = redisService.get(CACHE_ENCRYPT_KEY.concat(key), EncryptKey.class);
        redisService.del(CACHE_ENCRYPT_KEY.concat(key));
        return encryptKey;
    }

    @Override
    public String encrypt(EncryptKey key, String obj) throws EncryptException {
        try {
            final SM2 sm2 = SmUtil.sm2(null, key.getPublicKey());
            return sm2.encryptHex(obj, KeyType.PublicKey);
        } catch (Exception e) {
            throw new EncryptException("Redis SM2 加密异常");
        }
    }

    @Override
    public String decrypt(EncryptKey key, String obj) throws EncryptException {
        try {
            final SM2 sm2 = SmUtil.sm2(key.getPrivateKey(), null);
            return sm2.decryptStr(obj, KeyType.PrivateKey);
        } catch (Exception e) {
            throw new EncryptException("Redis SM2 解密异常");
        }
    }
}
