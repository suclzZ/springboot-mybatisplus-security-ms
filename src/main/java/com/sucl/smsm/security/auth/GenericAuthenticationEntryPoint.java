package com.sucl.smsm.security.auth;

import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * @see ExceptionTranslationFilter
 * 未登录或无权
 * 和AuthenticationFailureHandler的比较
 *
 * @author sucl
 * @date 2019/4/30
 */

public class GenericAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public GenericAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }
}
