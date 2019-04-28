package com.sucl.smsm.security.config;

import com.google.common.collect.Maps;
import com.sucl.smsm.security.user.DefaultUserDetailesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 在springboot中，如果想实现权限控制，则可以通过
 * : @EnableWebSecurity + WebSecurityConfigurerAdapter或者 @EnableWebSecurity + WebSecurityConfigurer
 *
 * @author sucl
 * @date 2019/4/28
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(WebSecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private WebSecurityProperties properties;
    private List<? extends AbstractAuthenticationProcessingFilter> authenticationProcessingFilters;

    protected WebSecurityConfig() {
        super();
    }

    protected WebSecurityConfig(boolean disableDefaults) {
        super(disableDefaults);
    }

    @Autowired(required = false)
    public void setAuthenticationProcessingFilters(List<? extends AbstractAuthenticationProcessingFilter> authenticationProcessingFilters) {
        this.authenticationProcessingFilters = authenticationProcessingFilters;
    }

    /**
     * 如果不配置，会在系统启动时，生成用户(user)、密码作为认证凭证
     * 认证配置：
     *  1、基于内存 auth.inMemoryAuthentication().withUser("user").password("password");
     *  2、基于指定数据源 auth.jdbcAuthentication().usersByUsernameQuery("select username,password,enabled from users where username = ?");
     *  3、基于名录库 auth.ldapAuthentication()...
     *  4、基于 UserDetailsService服务，需要自己实现该接口
     *  5、基于AuthenticationProvider，相对比较灵活，默认情况下authentication = UsernamePasswordAuthenticationToken，这个可以在我们的UserDetailsService中定义
     *  AuthenticationProvider有多种实现，比如aouth2
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    /**
     * 用户认证的具体实现
     * @return
     * @throws Exception
     */
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    protected UserDetailsService userDetailsService() {
//        return super.userDetailsService();
        return  new DefaultUserDetailesService();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/statics/**").anyRequest();
//        super.configure(web);
    }

    /**
     * 主要的认证逻辑在此
     * loginProcessingUrl : UsernamePasswordAuthenticationFilter
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        FormLoginConfigurer<HttpSecurity> login = http.formLogin();
        login.successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                    switch (getLoginType(request)){
                        case FORM:
                            break;
                        case AJAX:
                            Map<String,Object> resJson = Maps.newHashMap();
                            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                            response.getOutputStream().write(new JSONObject(resJson).toString().getBytes());
                            break;
                        case TOKEN:
                            break;
                    }
                }
            })
            .failureHandler(new AuthenticationFailureHandler() {
                @Override
                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                    onFailure(request,response,exception);
                }
            });
            login.usernameParameter(properties.getParamUsername()).passwordParameter(properties.getParamPassword())
//            .loginProcessingUrl("/login")//
            .successForwardUrl("/index").loginPage("/login").and().logout().logoutUrl("/logout");

        http.authorizeRequests()
            .antMatchers("/login").permitAll()//不需要认证
            .antMatchers("/**").fullyAuthenticated();

        //session失效后跳转
        http.sessionManagement().invalidSessionUrl("/login");
        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
    }

    private LoginType getLoginType(HttpServletRequest request) {
        String loginType = WebUtils.findParameterValue(request, "loginType");
        if(StringUtils.isEmpty(loginType)){
            return properties.getParamLoginType();
        }else{
            return LoginType.valueOf(loginType);
        }
    }

    private void onFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String message = "认证失败";
        if(exception instanceof UsernameNotFoundException){
            message = "用户不存在";
        }else if(exception instanceof BadCredentialsException){
            message = "密码错误";
        }else if(exception instanceof AccountStatusException){
            message = "状态异常";
        }
        request.setAttribute("error",message);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
//            response.getWriter().write(new ObjectMapper().writeValueAsString(new Message("999999",message)));
            response.getWriter().write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 匿名用户与rememberMe相关
     * @param trustResolver
     */
    @Override
    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        super.setTrustResolver(trustResolver);
    }

    /**
     * 通过请求获取 MediaType列表
     * @param contentNegotiationStrategy
     */
    @Override
    public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
        super.setContentNegotationStrategy(contentNegotiationStrategy);
    }

}
