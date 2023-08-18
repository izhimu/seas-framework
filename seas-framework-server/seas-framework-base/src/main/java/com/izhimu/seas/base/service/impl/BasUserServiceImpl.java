package com.izhimu.seas.base.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.BasAccount;
import com.izhimu.seas.base.entity.BasOrg;
import com.izhimu.seas.base.entity.BasUser;
import com.izhimu.seas.base.entity.BasUserRole;
import com.izhimu.seas.base.mapper.BasUserMapper;
import com.izhimu.seas.base.service.*;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.core.entity.DataPermission;
import com.izhimu.seas.core.entity.RefreshSession;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.data.entity.BaseEntity;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.security.config.SecurityConfig;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.security.service.SecurityService;
import com.izhimu.seas.security.util.SecurityUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.izhimu.seas.core.entity.DataPermission.NO_PERMISSION;

/**
 * 用户服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class BasUserServiceImpl extends BaseServiceImpl<BasUserMapper, BasUser> implements BasUserService, SecurityService {

    @Resource
    private BasAccountService accountService;
    @Resource
    private BasUserRoleService userRoleService;
    @Resource
    private BasRoleService roleService;
    @Resource
    private BasOrgService orgService;
    @Resource
    private EncryptService<EncryptKey, String> encryptService;
    @Resource
    private LoginHolder loginHolder;
    @Resource
    private SecurityConfig securityConfig;

    @Override
    public Page<BasUser> page(Page<BasUser> page, Object param) {
        QueryChainWrapper<BasUser> wrapper = this.paramQuery().param(param).orderBy().permissions().wrapper();
        if (param instanceof BasUser userParam) {
            Long orgId = userParam.getOrgId();
            if (Objects.nonNull(orgId)) {
                BasOrg org = orgService.getById(orgId);
                List<Long> list = orgService.lambdaQuery()
                        .select(BasOrg::getId)
                        .likeRight(BasOrg::getOrgCode, org.getOrgCode())
                        .list()
                        .stream()
                        .map(BasOrg::getId)
                        .toList();
                wrapper.in("org_id", list.isEmpty() ? List.of(NO_PERMISSION) : list);
            }
        }

        Page<BasUser> result = wrapper.page(page);
        for (BasUser user : result.getRecords()) {
            if (Objects.nonNull(user.getOrgId())) {
                BasOrg org = orgService.getById(user.getOrgId());
                if (Objects.nonNull(org)) {
                    user.setOrgName(org.getOrgName());
                }
            }
        }
        return result;
    }

    @Override
    public BasUser get(Long id) {
        BasUser user = this.getById(id);
        user.setAccounts(accountService.findByUserId(id));
        user.setRoleIds(userRoleService.findRoleIdByUserId(id));
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
        basUser.getAccounts()
                .stream()
                .filter(v -> Objects.nonNull(v.getUserAccount()))
                .forEach(newSysAccountConsumer(basUser, key, accountList));
        accountService.saveBatch(accountList);
        updateUserRole(basUser);
    }

    @Override
    public void updateUser(BasUser basUser) {
        this.updateById(basUser);
        if (basUser.getAccounts().isEmpty()) {
            accountService.removeByUserId(basUser.getId());
        } else {
            // 获取密钥
            AtomicReference<String> key = new AtomicReference<>();
            basUser.getAccounts().stream()
                    .filter(v -> StrUtil.isNotBlank(v.getPasswordKey()))
                    .findFirst()
                    .ifPresent(v -> key.set(v.getPasswordKey()));
            // 删除的账号
            List<Long> accountIdList = basUser.getAccounts().stream()
                    .map(BasAccount::getId)
                    .filter(Objects::nonNull)
                    .toList();
            accountService.removeByUserIdAndNotInId(basUser.getId(), accountIdList);
            // 新增的账号
            List<BasAccount> saveAccountList = new ArrayList<>();
            basUser.getAccounts().stream()
                    .filter(v -> Objects.isNull(v.getId()) && Objects.nonNull(v.getUserAccount()))
                    .forEach(newSysAccountConsumer(basUser, key, saveAccountList));
            accountService.saveBatch(saveAccountList);
            // 修改的账号
            List<BasAccount> updateAccountList = new ArrayList<>();
            basUser.getAccounts().stream()
                    .filter(v -> Objects.nonNull(v.getId()))
                    .forEach(v -> {
                        BasAccount basAccount = new BasAccount();
                        basAccount.setId(v.getId());
                        if (StrUtil.isNotBlank(v.getUserCertificate())) {
                            basAccount.setUserCertificate(BCrypt.hashpw(encryptService.decrypt(key.get(), v.getUserCertificate())));
                        }
                        if (basUser.getStatus() == 1) {
                            basAccount.setStatus(1);
                        } else {
                            basAccount.setStatus(v.getStatus());
                        }
                        updateAccountList.add(basAccount);
                    });
            accountService.updateBatchById(updateAccountList);
        }
        updateUserRole(basUser);
        EventManager.trigger(CoreEvent.E_SESSION_REFRESH, RefreshSession.user(basUser.getId()));
    }

    @Override
    public void delUser(Long id) {
        this.removeById(id);
        accountService.removeByUserId(id);
        userRoleService.removeByUserId(id);
        EventManager.trigger(CoreEvent.E_SESSION_REFRESH, RefreshSession.user(id));
    }

    @Override
    public List<BasUser> findUserList() {
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
            basAccount.setUserCertificate(BCrypt.hashpw(encryptService.decrypt(key.get(), v.getUserCertificate())));
            basAccount.setTypeCode(0);
            basAccount.setStatus(user.getStatus());
            accountList.add(basAccount);
        };
    }

    @Override
    public BasUser getCurrentUser() {
        User user = SecurityUtil.getUser();
        BasUser basUser = this.getById(user.getId());
        basUser.setAccount(user.getUserAccount());
        List<Long> roleIds = userRoleService.findRoleIdByUserId(user.getId());
        if (!roleIds.isEmpty()) {
            basUser.setRoleNames(roleService.findNameById(roleIds));
        }
        if (Objects.nonNull(user.getOrgId())) {
            BasOrg org = orgService.getById(user.getOrgId());
            if (Objects.nonNull(org)) {
                basUser.setOrgName(org.getOrgName());
            }
        }
        return basUser;
    }

    @Override
    public Map<Long, String> findUsernameMap(Collection<Long> ids) {
        return this.lambdaQuery()
                .select(BasUser::getId, BasUser::getUserName)
                .in(BasUser::getId, ids)
                .list()
                .stream()
                .collect(Collectors.toMap(BaseEntity::getId, BasUser::getUserName));
    }

    @Override
    public User loadUserByUsername(String username) {
        BasAccount account = accountService.getByAccount(username);
        if (Objects.isNull(account)) {
            return null;
        }
        BasUser basUser = this.get(account.getUserId());
        User user = new User();
        user.setId(account.getUserId());
        user.setUserAccount(account.getUserAccount());
        user.setUserCertificate(account.getUserCertificate());
        user.setLogicDel(account.getLogicDel());
        user.setStatus(account.getStatus());
        user.setTypeCode(account.getTypeCode());
        user.setNickName(basUser.getUserName());
        user.setOrgId(basUser.getOrgId());
        user.setLogin(loginHolder.get(false));
        user.setIsSuper(securityConfig.getSupers().contains(account.getUserAccount()));
        // 菜单权限
        List<String> menuAuth = roleService.findMenuAuthByUserId(user);
        // 数据权限
        DataPermission dataPermission = roleService.getDataPermissionByUserId(user);
        user.setMenuAuth(menuAuth);
        user.setDataAuth(dataPermission);
        return user;
    }

    private void updateUserRole(BasUser user) {
        userRoleService.removeByUserId(user.getId());
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
}
