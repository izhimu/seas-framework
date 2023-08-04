package com.izhimu.seas.security.provider;

import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.security.service.SecurityService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;

/**
 * 用户认证方法提供类
 * 处理用户登录操作
 *
 * @author haoran
 * @version v1.0
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private final SecurityService securityService;

    private final List<String> supers;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, SecurityService securityService, List<String> supers) {
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.supers = supers;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        User userDetails = (User) securityService.loadUserByUsername(username);
        if (Objects.isNull(userDetails)) {
            throw new BadCredentialsException("账号或密码错误");
        }
        // 密码校验
        if (!passwordEncoder.matches(password, userDetails.getUserCertificate())) {
            throw new BadCredentialsException("账号或密码错误");
        }
        // 状态校验
        if (!userDetails.isAccountNonLocked() || !userDetails.isEnabled()) {
            throw new DisabledException("账号被禁用");
        }
        userDetails.setIsSuper(supers.contains(userDetails.getUserAccount()));

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                userDetails,
                authentication.getCredentials(),
                userDetails.getAuthorities()
        );
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
