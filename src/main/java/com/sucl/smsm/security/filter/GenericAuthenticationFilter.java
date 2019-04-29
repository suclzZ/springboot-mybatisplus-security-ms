package com.sucl.smsm.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录请求拦截
 * 结合  AuthenticationProvider
 * 通过返回的Authentication 即 AbstractAuthenticationToken
 * 在 AuthenticationProvider 中构建不同的逻辑
 * @author sucl
 * @date 2019/4/29
 */
public class GenericAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected GenericAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
