package com.sucl.smsm.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sucl
 * @date 2019/4/28
 */
@Data
@ConfigurationProperties(prefix = "web.security")
public class WebSecurityProperties {
    /**
     * form表单用户名参数
     */
    private String paramUsername = "username";

    /**
     * form表单密码参数
     */
    private String paramPassword = "password";

    /**
     * 认证类型类型
     */
    private LoginType loginType = LoginType.FORM;

    /**
     * 登录请求
     */
    private String loginUrl = "/login";
    /**
     * 登录成功后跳转
     */
    private String loginSuccess = "/index";
    /**
     * 登出
     */
    private String logout = "/logout";
}
