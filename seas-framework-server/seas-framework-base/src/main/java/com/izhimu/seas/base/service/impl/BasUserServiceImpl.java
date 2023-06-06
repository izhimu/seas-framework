package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.izhimu.seas.base.entity.BasAccount;
import com.izhimu.seas.base.entity.BasUser;
import com.izhimu.seas.base.mapper.BasUserMapper;
import com.izhimu.seas.base.service.BasAccountService;
import com.izhimu.seas.base.service.BasUserService;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.utils.SecurityUtil;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.security.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 用户服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasUserServiceImpl extends BaseServiceImpl<BasUserMapper, BasUser> implements BasUserService, SecurityService {

    @Resource
    private BasAccountService accountService;

    @Resource
    private EncryptService<EncryptKey, String> encryptService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private LoginHolder loginHolder;

    @Override
    public BasUser get(Long id) {
        BasUser user = this.getById(id);
        List<BasAccount> accountVOList = accountService.getByUserId(id);
        user.setAccounts(accountVOList);
        return user;
    }

    @Override
    public void saveUser(BasUser basUser) {
        this.save(basUser);
        if (basUser.getAccounts().isEmpty()) {
            return;
        }
        // 账号处理
        AtomicReference<String> key = new AtomicReference<>();
        basUser.getAccounts().stream().findFirst().ifPresent(item -> key.set(item.getPasswordKey()));
        List<BasAccount> accountList = new ArrayList<>();
        basUser.getAccounts().forEach(newSysAccountConsumer(basUser, key, accountList));
        accountService.saveBatch(accountList);
    }

    @Override
    public void updateUser(BasUser basUser) {
        this.updateById(basUser);
        if (basUser.getAccounts().isEmpty()) {
            accountService.lambdaUpdate()
                    .eq(BasAccount::getUserId, basUser.getId())
                    .remove();
            return;
        }
        // 获取密钥
        AtomicReference<String> key = new AtomicReference<>();
        basUser.getAccounts().stream()
                .filter(v -> StringUtils.isNotBlank(v.getPasswordKey()))
                .findFirst()
                .ifPresent(v -> key.set(v.getPasswordKey()));
        // 删除的账号
        List<Long> accountIdList = basUser.getAccounts().stream()
                .map(BasAccount::getId).toList();
        List<BasAccount> delIdList = accountService.lambdaQuery()
                .select(BasAccount::getId)
                .eq(BasAccount::getUserId, basUser.getId())
                .list()
                .stream()
                .filter(v -> !accountIdList.contains(v.getId()))
                .toList();
        accountService.removeBatchByIds(delIdList);
        // 新增的账号
        List<BasAccount> saveAccountList = new ArrayList<>();
        basUser.getAccounts().stream()
                .filter(v -> Objects.isNull(v.getId()))
                .forEach(newSysAccountConsumer(basUser, key, saveAccountList));
        accountService.saveBatch(saveAccountList);
        // 修改的账号
        List<BasAccount> updateAccountList = new ArrayList<>();
        basUser.getAccounts().stream()
                .filter(v -> Objects.nonNull(v.getId()) && StringUtils.isNotBlank(v.getUserCertificate()))
                .forEach(v -> {
                    BasAccount basAccount = new BasAccount();
                    basAccount.setId(v.getId());
                    basAccount.setUserCertificate(passwordEncoder.encode(encryptService.decrypt(key.get(), v.getUserCertificate())));
                    updateAccountList.add(basAccount);
                });
        accountService.updateBatchById(updateAccountList);
    }

    @Override
    public void delUser(Long id) {
        this.removeById(id);
        accountService.lambdaUpdate()
                .eq(BasAccount::getUserId, id)
                .remove();
    }

    @Override
    public List<BasUser> getUserList() {
        return this.list();
    }

    @Override
    public List<Select<String>> likeUser(String search) {
        return this.lambdaQuery()
                .select(BasUser::getId, BasUser::getUserName)
                .like(BasUser::getUserName, search)
                .list()
                .stream()
                .map(v -> new Select<>(v.getUserName(), String.valueOf(v.getId())))
                .toList();
    }

    /**
     * 新增账号
     *
     * @param user        SysUser
     * @param key         AtomicReference
     * @param accountList List
     * @return Consumer
     */
    private Consumer<BasAccount> newSysAccountConsumer(BasUser user, AtomicReference<String> key, List<BasAccount> accountList) {
        return v -> {
            BasAccount basAccount = new BasAccount();
            basAccount.setUserId(user.getId());
            basAccount.setUserAccount(v.getUserAccount());
            basAccount.setUserCertificate(passwordEncoder.encode(encryptService.decrypt(key.get(), v.getUserCertificate())));
            basAccount.setTypeCode(0);
            basAccount.setStatus(0);
            accountList.add(basAccount);
        };
    }

    @Override
    public User getCurrentUser() {
        return SecurityUtil.contextUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BasAccount> sysAccount = accountService.lambdaQuery()
                .eq(BasAccount::getUserAccount, username)
                .oneOpt();
        if (sysAccount.isEmpty()) {
            return null;
        }
        BasUser basUser = this.get(sysAccount.get().getUserId());
        BasAccount account = sysAccount.get();
        User user = new User();
        user.setId(account.getUserId());
        user.setUserAccount(account.getUserAccount());
        user.setUserCertificate(account.getUserCertificate());
        user.setLogicDel(account.getLogicDel());
        user.setStatus(account.getStatus());
        user.setTypeCode(account.getTypeCode());
        user.setNickName(basUser.getUserName());
        user.setLogin(loginHolder.get(false));
        return user;
    }
}
