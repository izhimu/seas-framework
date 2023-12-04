package com.izhimu.seas.security.service.impl;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.util.SaFoxUtil;
import com.izhimu.seas.cache.service.CacheService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haoran
 */
@Service
public class TokenCacheServiceImpl implements SaTokenDao {

    private final CacheService cacheService;

    public TokenCacheServiceImpl(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public String get(String key) {
        return cacheService.get(key, String.class);
    }

    @Override
    public void set(String key, String value, long timeout) {
        if (timeout != 0L && timeout > -2L) {
            if (timeout == -1L) {
                cacheService.set(key, value);
            } else {
                cacheService.set(key, value, timeout);
            }
        }
    }

    @Override
    public void update(String key, String value) {
        long timeout = this.getTimeout(key);
        if (timeout > -1L) {
            this.set(key, value, timeout);
        }
    }

    @Override
    public void delete(String key) {
        cacheService.del(key);
    }

    @Override
    public long getTimeout(String key) {
        return cacheService.getExpire(key);
    }

    @Override
    public void updateTimeout(String key, long timeout) {
        cacheService.setExpire(key, timeout);
    }

    @Override
    public Object getObject(String key) {
        return cacheService.get(key);
    }

    @Override
    public void setObject(String key, Object object, long timeout) {
        if (timeout != 0L && timeout > -2L) {
            if (timeout == -1L) {
                cacheService.set(key, object);
            } else {
                cacheService.set(key, object, timeout);
            }
        }
    }

    @Override
    public void updateObject(String key, Object object) {
        long timeout = this.getObjectTimeout(key);
        if (timeout > -1L) {
            this.setObject(key, object, timeout);
        }
    }

    @Override
    public void deleteObject(String key) {
        cacheService.del(key);
    }

    @Override
    public long getObjectTimeout(String key) {
        return cacheService.getExpire(key);
    }

    @Override
    public void updateObjectTimeout(String key, long timeout) {
        cacheService.setExpire(key, timeout);
    }

    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        return SaFoxUtil.searchList(new ArrayList<>(cacheService.keys(prefix + "*" + keyword + "*")), start, size, sortType);
    }
}
