package com.sucl.smsm.security.service;

import com.sucl.smsm.security.config.LoginType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

/**
 * 登录成功后的处理适配
 * 针对{@link LoginType}
 *
 * @author sucl
 * @date 2019/4/29
 */
public interface AuthenticationHandlerAdapter {

    boolean support(LoginType loginType);

    void config(FormLoginConfigurer<HttpSecurity> login);
}
