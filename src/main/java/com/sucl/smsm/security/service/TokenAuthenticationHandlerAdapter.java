package com.sucl.smsm.security.service;

import com.sucl.smsm.security.config.LoginType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author sucl
 * @date 2019/4/29
 */
@Component
public class TokenAuthenticationHandlerAdapter implements AuthenticationHandlerAdapter{
    @Override
    public boolean support(LoginType loginType) {
        return loginType == LoginType.TOKEN;
    }

    @Override
    public void config(FormLoginConfigurer<HttpSecurity> login) {
        login.successHandler(new TokenAuthenticationSuccessHandler());
        login.failureHandler(new TokenAuthenticationFailureHandler());
    }
}
