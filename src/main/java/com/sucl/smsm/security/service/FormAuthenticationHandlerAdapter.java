package com.sucl.smsm.security.service;

import com.sucl.smsm.security.config.LoginType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author sucl
 * @date 2019/4/29
 */
@Component
public class FormAuthenticationHandlerAdapter implements AuthenticationHandlerAdapter{
    @Override
    public boolean support(LoginType loginType) {
        return loginType == LoginType.FORM;
    }
    @Value("${web.security.login-url:/login}")
    public String defaultFailureUrl;

    @Override
    public void config(FormLoginConfigurer<HttpSecurity> login) {
        login.successHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        login.failureHandler(new SimpleUrlAuthenticationFailureHandler(defaultFailureUrl));
    }
}
