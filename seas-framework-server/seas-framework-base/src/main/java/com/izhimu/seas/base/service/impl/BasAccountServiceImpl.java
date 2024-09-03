package com.izhimu.seas.base.service.impl;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.entity.BasAccount;
import com.izhimu.seas.base.mapper.BasAccountMapper;
import com.izhimu.seas.base.service.BasAccountService;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.entity.BaseEntity;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户账号服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasAccountServiceImpl extends BaseServiceImpl<BasAccountMapper, BasAccount> implements BasAccountService {

    @Resource
    private EncryptService<EncryptKey, String> encryptService;

    @Override
    public List<BasAccount> findByUserId(Long userId) {
        return this.lambdaQuery()
                .eq(BasAccount::getUserId, userId)
                .list();
    }

    @Override
    public BasAccount getByAccount(String account) {
        return this.lambdaQuery()
                .eq(BasAccount::getUserAccount, account)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Select<String>> likeAccount(String search) {
        return this.lambdaQuery()
                .select(BasAccount::getId, BasAccount::getUserAccount)
                .like(BasAccount::getUserAccount, search)
                .list()
                .stream()
                .map(v -> new Select<>(v.getUserAccount(), String.valueOf(v.getId())))
                .toList();
    }

    @Override
    public Map<Long, String> findAccountMap(Collection<Long> ids) {
        return this.lambdaQuery()
                .select(BasAccount::getId, BasAccount::getUserAccount)
                .in(BasAccount::getId, ids)
                .list()
                .stream()
                .collect(Collectors.toMap(BaseEntity::getId, BasAccount::getUserAccount));
    }

    @Override
    public boolean removeByUserId(Long userId) {
        return this.lambdaUpdate()
                .eq(BasAccount::getUserId, userId)
                .remove();
    }

    @Override
    public boolean removeByUserIdAndNotInId(Long userId, List<Long> ids) {
        List<BasAccount> delIdList = this.lambdaQuery()
                .select(BasAccount::getId)
                .eq(BasAccount::getUserId, userId)
                .notIn(!ids.isEmpty(), BasAccount::getId, ids)
                .list();
        if (delIdList.isEmpty()) {
            return true;
        }
        return SpringUtil.getBean(BasAccountService.class).removeBatchByIds(delIdList);
    }

    @Override
    public boolean checkAccount(BasAccount account) {
        return !this.lambdaQuery()
                .ne(Objects.nonNull(account.getId()), BasAccount::getId, account.getId())
                .eq(BasAccount::getUserAccount, account.getUserAccount())
                .exists();
    }

    @Override
    public boolean changePassword(BasAccount account) throws ValidateException {
        String oldPassword = encryptService.decrypt(account.getPasswordKey(), account.getOriginalCertificate());
        BasAccount dbAccount = getByAccount(account.getUserAccount());
        if (!BCrypt.checkpw(oldPassword, dbAccount.getUserCertificate())) {
            throw new ValidateException("原密码错误");
        }
        String newPassword = BCrypt.hashpw(encryptService.decrypt(account.getPasswordKey(), account.getUserCertificate()));
        return this.lambdaUpdate()
                .eq(BasAccount::getUserAccount, account.getUserAccount())
                .set(BasAccount::getUserCertificate, newPassword)
                .update();
    }
}
