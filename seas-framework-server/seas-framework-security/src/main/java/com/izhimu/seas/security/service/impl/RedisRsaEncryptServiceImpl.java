package com.izhimu.seas.security.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.izhimu.seas.cache.service.RedisService;
import com.izhimu.seas.security.entity.EncryptKey;
import com.izhimu.seas.security.enums.EncryptType;
import com.izhimu.seas.security.exception.EncryptException;
import com.izhimu.seas.security.service.EncryptService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
@Service("RedisRSAEncryptService")
@ConditionalOnClass(value = RedisService.class)
public class RedisRsaEncryptServiceImpl implements EncryptService<EncryptKey, String> {

    private static final String CACHE_ENCRYPT_KEY = "encrypt:rsa.key.";

    @Resource
    private RedisService redisService;

    @Override
    public EncryptKey createEncryptKey(long timeout) {
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        String key = IdUtil.getSnowflakeNextIdStr();
        String publicKey = Base64.encode(keyPair.getPublic().getEncoded());
        String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
        EncryptKey encryptKey = new EncryptKey(key, EncryptType.RSA, publicKey, privateKey, Instant.now().toEpochMilli());
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
            RSA rsa = new RSA(key.getPublicKey(), null);
            byte[] encrypt = rsa.encrypt(CharSequenceUtil.bytes(obj, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
            return StrUtil.str(encrypt, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            throw new EncryptException("Redis RSA 加密异常");
        }
    }

    @Override
    public String decrypt(EncryptKey key, String obj) throws EncryptException {
        try {
            RSA rsa = new RSA(key.getPrivateKey(), null);
            byte[] decrypt = rsa.decrypt(obj, KeyType.PrivateKey);
            return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            throw new EncryptException("Redis RSA 解密异常");
        }
    }
}
