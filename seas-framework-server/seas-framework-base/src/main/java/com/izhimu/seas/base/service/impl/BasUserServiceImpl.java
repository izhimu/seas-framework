package com.izhimu.seas.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.*;
import com.izhimu.seas.base.mapper.BasUserMapper;
import com.izhimu.seas.base.service.*;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.utils.SecurityUtil;
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
    private BasUserRoleService userRoleService;

    @Resource
    private BasUserOrgService userOrgService;

    @Resource
    private BasRoleService roleService;

    @Resource
    private BasOrgService orgService;

    @Resource
    private EncryptService<EncryptKey, String> encryptService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private LoginHolder loginHolder;

    @Override
    public Page<BasUser> page(Page<BasUser> page, Object param) {
        Page<BasUser> result = super.page(page, param);
        for (BasUser record : result.getRecords()) {
            List<BasUserOrg> list = userOrgService.lambdaQuery()
                    .select(BasUserOrg::getOrgId)
                    .eq(BasUserOrg::getUserId, record.getId())
                    .list();
            if (!list.isEmpty()) {
                record.setOrgId(list.get(0).getOrgId());
                BasOrg org = orgService.getById(record.getOrgId());
                record.setOrgName(org.getOrgName());
            }
        }
        return result;
    }

    @Override
    public BasUser get(Long id) {
        BasUser user = this.getById(id);
        List<BasAccount> accountVOList = accountService.getByUserId(id);
        user.setAccounts(accountVOList);
        List<Long> roleList = userRoleService.lambdaQuery()
                .select(BasUserRole::getRoleId)
                .eq(BasUserRole::getUserId, id)
                .list()
                .stream()
                .map(BasUserRole::getRoleId)
                .toList();
        user.setRoleIds(roleList);
        List<BasUserOrg> orgList = userOrgService.lambdaQuery()
                .select(BasUserOrg::getOrgId)
                .eq(BasUserOrg::getUserId, id)
                .list();
        if (!orgList.isEmpty()) {
            user.setOrgId(orgList.get(0).getOrgId());
        }
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
        updateUserRole(basUser);
        updateUserOrg(basUser);
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
                .filter(v -> StrUtil.isNotBlank(v.getPasswordKey()))
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
        // 修改密码的账号
        List<BasAccount> updateAccountList = new ArrayList<>();
        basUser.getAccounts().stream()
                .filter(v -> Objects.nonNull(v.getId()))
                .forEach(v -> {
                    BasAccount basAccount = new BasAccount();
                    basAccount.setId(v.getId());
                    if (StrUtil.isNotBlank(v.getUserCertificate())) {
                        basAccount.setUserCertificate(passwordEncoder.encode(encryptService.decrypt(key.get(), v.getUserCertificate())));
                    }
                    basAccount.setStatus(basUser.getStatus());
                    updateAccountList.add(basAccount);
                });
        accountService.updateBatchById(updateAccountList);
        updateUserRole(basUser);
        updateUserOrg(basUser);
    }

    @Override
    public void delUser(Long id) {
        this.removeById(id);
        accountService.lambdaUpdate()
                .eq(BasAccount::getUserId, id)
                .remove();
        userRoleService.lambdaUpdate()
                .eq(BasUserRole::getUserId, id)
                .remove();
        userOrgService.lambdaUpdate()
                .eq(BasUserOrg::getUserId, id)
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
            basAccount.setStatus(user.getStatus());
            accountList.add(basAccount);
        };
    }

    @Override
    public User getCurrentUser() {
        return SecurityUtil.contextUser();
    }

    @Override
    public BasUser current() {
        User user = getCurrentUser();
        BasUser basUser = this.getById(user.getId());
        basUser.setAccount(user.getUserAccount());
        List<Long> roleIds = userRoleService.lambdaQuery()
                .select(BasUserRole::getRoleId)
                .eq(BasUserRole::getUserId, user.getId())
                .list()
                .stream()
                .map(BasUserRole::getRoleId)
                .toList();
        if (!roleIds.isEmpty()) {
            List<String> roleNames = roleService.lambdaQuery()
                    .select(BasRole::getRoleName)
                    .in(BasRole::getId, roleIds)
                    .list()
                    .stream()
                    .map(BasRole::getRoleName)
                    .distinct()
                    .toList();
            basUser.setRoleNames(roleNames);
        }
        List<BasUserOrg> orgList = userOrgService.lambdaQuery()
                .select(BasUserOrg::getOrgId)
                .eq(BasUserOrg::getUserId, user.getId())
                .list();
        if (!orgList.isEmpty()) {
            BasOrg org = orgService.getById(orgList.get(0).getOrgId());
            if (Objects.nonNull(org)) {
                basUser.setOrgName(org.getOrgName());
            }
        }
        return basUser;
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

    private void updateUserRole(BasUser user) {
        userRoleService.lambdaUpdate()
                .eq(BasUserRole::getUserId, user.getId())
                .remove();
        if (Objects.nonNull(user.getRoleIds())) {
            List<BasUserRole> userRoleList = user.getRoleIds().stream()
                    .map(v -> {
                        BasUserRole data = new BasUserRole();
                        data.setUserId(user.getId());
                        data.setRoleId(v);
                        return data;
                    })
                    .toList();
            userRoleService.saveBatch(userRoleList);
        }
    }

    private void updateUserOrg(BasUser user) {
        userOrgService.lambdaUpdate()
                .eq(BasUserOrg::getUserId, user.getId())
                .remove();
        if (Objects.nonNull(user.getOrgId())) {
            BasUserOrg userOrg = new BasUserOrg();
            userOrg.setUserId(user.getId());
            userOrg.setOrgId(user.getOrgId());
            userOrgService.save(userOrg);
        }
    }
}
