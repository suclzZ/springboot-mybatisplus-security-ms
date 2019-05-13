package com.sucl.smsm.security.auth;

import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * @see ExceptionTranslationFilter
 * 和AuthenticationFailureHandler的比较
 * 未登录或无权时的跳转处理
 * eg. 访问资源 /hello需要认证才行，则会通过该类处理
 *
 *
 * @author sucl
 * @date 2019/4/30
 */

public class GenericAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public GenericAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    public GenericAuthenticationEntryPoint() {
        super("/glogin");
    }
}
