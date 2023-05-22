package com.izhimu.seas.base.service.impl;

import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.izhimu.seas.base.dto.SysAccountDTO;
import com.izhimu.seas.base.dto.SysUserDTO;
import com.izhimu.seas.base.entity.SysAccount;
import com.izhimu.seas.base.entity.SysUser;
import com.izhimu.seas.base.mapper.SysUserMapper;
import com.izhimu.seas.base.service.SysAccountService;
import com.izhimu.seas.base.service.SysUserService;
import com.izhimu.seas.base.vo.SysAccountVO;
import com.izhimu.seas.base.vo.SysUserVO;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.utils.SecurityUtil;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.cache.service.EncryptService;
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
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService, SecurityService {

    @Resource
    private SysAccountService accountService;

    @Resource
    private EncryptService<EncryptKey, String> encryptService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private LoginHolder loginHolder;

    @Override
    public SysUserVO get(Long id) {
        SysUserVO sysUserVO = super.get(id, SysUserVO.class);
        List<SysAccountVO> accountVOList = accountService.getByUserId(id);
        sysUserVO.setAccounts(accountVOList);
        return sysUserVO;
    }

    @Override
    public void saveUser(SysUserDTO sysUser) {
        SysUser user = CglibUtil.copy(sysUser, SysUser.class);
        this.save(user);
        if (sysUser.getAccounts().isEmpty()) {
            return;
        }
        // 账号处理
        AtomicReference<String> key = new AtomicReference<>();
        sysUser.getAccounts().stream().findFirst().ifPresent(item -> key.set(item.getPasswordKey()));
        List<SysAccount> accountList = new ArrayList<>();
        sysUser.getAccounts().forEach(newSysAccountConsumer(user, key, accountList));
        accountService.saveBatch(accountList);
    }

    @Override
    public void updateUser(SysUserDTO sysUser) {
        SysUser user = CglibUtil.copy(sysUser, SysUser.class);
        this.updateById(user);
        if (sysUser.getAccounts().isEmpty()) {
            accountService.lambdaUpdate()
                    .eq(SysAccount::getUserId, user.getId())
                    .remove();
            return;
        }
        // 获取密钥
        AtomicReference<String> key = new AtomicReference<>();
        sysUser.getAccounts().stream()
                .filter(v -> StringUtils.isNotBlank(v.getPasswordKey()))
                .findFirst()
                .ifPresent(v -> key.set(v.getPasswordKey()));
        // 删除的账号
        List<Long> accountIdList = sysUser.getAccounts().stream()
                .map(SysAccountDTO::getId).toList();
        List<SysAccount> delIdList = accountService.lambdaQuery()
                .select(SysAccount::getId)
                .eq(SysAccount::getUserId, user.getId())
                .list()
                .stream()
                .filter(v -> !accountIdList.contains(v.getId()))
                .toList();
        accountService.removeBatchByIds(delIdList);
        // 新增的账号
        List<SysAccount> saveAccountList = new ArrayList<>();
        sysUser.getAccounts().stream()
                .filter(v -> Objects.isNull(v.getId()))
                .forEach(newSysAccountConsumer(user, key, saveAccountList));
        accountService.saveBatch(saveAccountList);
        // 修改的账号
        List<SysAccount> updateAccountList = new ArrayList<>();
        sysUser.getAccounts().stream()
                .filter(v -> Objects.nonNull(v.getId()) && StringUtils.isNotBlank(v.getUserCertificate()))
                .forEach(v -> {
                    SysAccount sysAccount = new SysAccount();
                    sysAccount.setId(v.getId());
                    sysAccount.setUserCertificate(passwordEncoder.encode(encryptService.decrypt(key.get(), v.getUserCertificate())));
                    updateAccountList.add(sysAccount);
                });
        accountService.updateBatchById(updateAccountList);
    }

    @Override
    public void delUser(Long id) {
        this.removeById(id);
        accountService.lambdaUpdate()
                .eq(SysAccount::getUserId, id)
                .remove();
    }

    @Override
    public List<SysUserVO> getUserList() {
        return CglibUtil.copyList(this.list(), SysUserVO::new);
    }

    @Override
    public List<Select<String>> likeUser(String search) {
        return this.lambdaQuery()
                .select(SysUser::getId, SysUser::getUserName)
                .like(SysUser::getUserName, search)
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
    private Consumer<SysAccountDTO> newSysAccountConsumer(SysUser user, AtomicReference<String> key, List<SysAccount> accountList) {
        return v -> {
            SysAccount sysAccount = new SysAccount();
            sysAccount.setUserId(user.getId());
            sysAccount.setUserAccount(v.getUserAccount());
            sysAccount.setUserCertificate(passwordEncoder.encode(encryptService.decrypt(key.get(), v.getUserCertificate())));
            sysAccount.setTypeCode(0);
            sysAccount.setStatus(0);
            accountList.add(sysAccount);
        };
    }

    @Override
    public User getCurrentUser() {
        return SecurityUtil.contextUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SysAccount> sysAccount = accountService.lambdaQuery()
                .eq(SysAccount::getUserAccount, username)
                .oneOpt();
        if (sysAccount.isEmpty()) {
            return null;
        }
        SysUserVO sysUserVO = this.get(sysAccount.get().getUserId());
        SysAccount account = sysAccount.get();
        User user = new User();
        user.setId(account.getUserId());
        user.setUserAccount(account.getUserAccount());
        user.setUserCertificate(account.getUserCertificate());
        user.setLogicDel(account.getLogicDel());
        user.setStatus(account.getStatus());
        user.setTypeCode(account.getTypeCode());
        user.setNickName(sysUserVO.getUserName());
        user.setLogin(loginHolder.get(false));
        return user;
    }
}
