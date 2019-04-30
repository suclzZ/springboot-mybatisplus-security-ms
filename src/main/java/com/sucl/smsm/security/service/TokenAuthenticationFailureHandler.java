package com.sucl.smsm.security.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败的处理逻辑，
 * 默认是 {@link SimpleUrlAuthenticationFailureHandler}
 * @author sucl
 * @date 2019/4/29
 */
public class TokenAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        onFailure(request,response,exception);
    }

    private void onFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String message = exception.getMessage();
        if(exception instanceof UsernameNotFoundException){
            message = "用户不存在";
        }else if(exception instanceof BadCredentialsException){
            message = "密码错误";
        }else if(exception instanceof AccountStatusException){
            message = "状态异常";
        }
        request.setAttribute("error","认证失败:" +message);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
//            response.getWriter().write(new ObjectMapper().writeValueAsString(new Message("999999",message)));
            response.getWriter().write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
