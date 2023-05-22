package com.izhimu.seas.cache.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.izhimu.seas.cache.enums.EncryptType;
import com.izhimu.seas.cache.exception.EncryptException;
import com.izhimu.seas.cache.service.RedisService;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.EncryptService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.time.Instant;

/**
 * 基于Redis实现的RSA加密服务
 *
 * @author haoran
 * @version v1.0
 */
@Service
@ConditionalOnClass(value = RedisService.class)
@ConditionalOnProperty(prefix = "seas.security", name = "encrypt-mode", havingValue = "RSA")
public class RedisRsaEncryptServiceImpl implements EncryptService<EncryptKey, String> {

    @Resource
    private RedisService redisService;

    @Override
    public EncryptKey createEncryptKey(long timeout) {
        String key = IdUtil.nanoId();

        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        String publicKey = Base64.encode(keyPair.getPublic().getEncoded());
        String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());

        EncryptKey encryptKey = new EncryptKey(key, EncryptType.RSA, publicKey, privateKey, Instant.now().toEpochMilli());
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
            RSA rsa = new RSA(encryptKey.getPrivateKey(), encryptKey.getPublicKey());
            byte[] encrypt = rsa.encrypt(CharSequenceUtil.bytes(obj, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
            return StrUtil.str(encrypt, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            throw new EncryptException("Redis RSA 加密异常");
        }
    }

    @Override
    public String decrypt(String key, String obj) throws EncryptException {
        try {
            EncryptKey encryptKey = getEncryptKey(key);
            RSA rsa = new RSA(encryptKey.getPrivateKey(), encryptKey.getPublicKey());
            byte[] decrypt = rsa.decrypt(obj, KeyType.PrivateKey);
            return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            throw new EncryptException("Redis RSA 解密异常");
        }
    }
}
