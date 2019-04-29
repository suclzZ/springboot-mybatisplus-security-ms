package com.sucl.smsm.security.service;

import com.google.common.collect.Maps;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 针对不同登录方式的成功的处理
 * 默认使用的是：SavedRequestAwareAuthenticationSuccessHandler，也就是form提交
 * 但如果用ajax登录则不使用
 * @author sucl
 * @date 2019/4/29
 */
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String,Object> resJson = Maps.newHashMap();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getOutputStream().write(new JSONObject(resJson).toString().getBytes());
    }
}
