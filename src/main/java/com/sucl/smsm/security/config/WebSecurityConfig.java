package com.sucl.smsm.security.config;

import com.sucl.smsm.security.auth.GenericAuthenticationEntryPoint;
import com.sucl.smsm.security.auth.PreAbstractAuthenticationFilter;
import com.sucl.smsm.security.service.AuthenticationHandlerAdapter;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 配置项
     */
    @Autowired
    private WebSecurityProperties properties;
    /**
     * 如果有值，则使用默认的{@link DaoAuthenticationProvider}，同时作为Parent Provinder
     */
    @Autowired(required = false)
    private UserDetailsService userDetailsService;
    /**
     * 前置过滤器配置，设置在{@link UsernamePasswordAuthenticationFilter}前
     */
    private List<PreAbstractAuthenticationFilter> preAuthenticationProcessingFilters;
    /**
     * 认证相关，支持多种认证方式
     */
    private List<AuthenticationProvider> authenticationProviders;
    /**
     * 认证成功/失败处理器，根据不同类型的认证方式进行扩展
     */
    private List<AuthenticationHandlerAdapter> authenticationHandlerAdapters;

    protected WebSecurityConfig() {
        super( false);
    }

    protected WebSecurityConfig(boolean disableDefaults) {
        super(disableDefaults);
    }

    @Autowired(required = false)
    public void setPreAuthenticationProcessingFilters(List<PreAbstractAuthenticationFilter> preAuthenticationProcessingFilters) {
        this.preAuthenticationProcessingFilters = preAuthenticationProcessingFilters;
    }

    @Autowired(required = false)
    public void setAuthenticationProviders(List<AuthenticationProvider> authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }

    /**
     * 如果不配置，会在系统启动时，生成用户(user)、密码作为认证凭证
     * 认证配置：
     *  1、基于内存 auth.inMemoryAuthentication().withUser("user").password("password");
     *  2、基于指定数据源 auth.jdbcAuthentication().usersByUsernameQuery("select username,password,enabled from users where username = ?");
     *  3、基于名录库 auth.ldapAuthentication()...
     *  4、基于 UserDetailsService服务，需要自己实现该接口
     *  5、基于AuthenticationProvider，相对比较灵活，默认情况下authentication = UsernamePasswordAuthenticationToken，这个可以在我们的UserDetailsService中定义,
     *  AuthenticationProvider有多种实现，比如aouth2
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if(userDetailsService!=null){//parentAuthenticationManager
            auth.userDetailsService(userDetailsService);
        }
        if(authenticationProviders!=null && authenticationProviders.size()>0){
            for(AuthenticationProvider authenticationProvider : authenticationProviders){
                auth.authenticationProvider(authenticationProvider);
            }
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**").antMatchers("/favicon.ico")
                .mvcMatchers("/webjars/**")
                .mvcMatchers("/js/**")
                .mvcMatchers("/css/**");
    }

    /**
     * 主要配置在此:
     * 从DefaultLoginPageGeneratingFilter中我们得知，对于请求/login，都会进入这个配置的默认登录界面，那如何进入我们自定义的登录界面？
     * 从UsernamePasswordAuthenticationFilter中我们需要对登录请求进行配置，loginProcessingUrl默认为[/login POST],换句话说，对于这样的请求就会进入 这个过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        //权限控制
//        http.authorizeRequests()
//                .mvcMatchers("").access("hasRole('ADMIN') or hasRole('USER')")//表达式
//                .mvcMatchers("").hasRole("ADMIN")//角色，默认ROLE_前缀
//                .mvcMatchers("").hasAuthority("ROLE_ADMIN");//无ROLE_前缀
        //认证控制
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET,properties.getLoginUrl()).permitAll()
            .anyRequest().authenticated()
            .and().exceptionHandling().authenticationEntryPoint(new GenericAuthenticationEntryPoint(properties.getLoginUrl()))
            .and().logout().permitAll();
        //
        http.rememberMe().rememberMeParameter("rememberMe");
//        http.anonymous();
        //form表单
        FormLoginConfigurer<HttpSecurity> login = http.formLogin()
                .loginPage(properties.getLoginUrl())
                .defaultSuccessUrl(properties.getLoginSuccess())
                .failureForwardUrl(properties.getLoginUrl())
                .failureUrl(properties.getLoginUrl())
                .usernameParameter(properties.getParamUsername())
                .passwordParameter(properties.getParamPassword());

        configFormlogin(login);

        http.rememberMe();
        //session失效后跳转
        http.sessionManagement().invalidSessionUrl(properties.getLoginUrl());
        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl(properties.getLoginUrl());
        //前置过滤器
        if(preAuthenticationProcessingFilters!=null){
            for(PreAbstractAuthenticationFilter preAuthenticationProcessingFilter :preAuthenticationProcessingFilters){
                http.addFilterBefore(preAuthenticationProcessingFilter,UsernamePasswordAuthenticationFilter.class);
            }
        }
    }

    protected FormLoginConfigurer<HttpSecurity> configFormlogin(FormLoginConfigurer<HttpSecurity> login) {
        if(authenticationHandlerAdapters!=null){
            for(AuthenticationHandlerAdapter authenticationHandlerAdapter : authenticationHandlerAdapters){
                if(authenticationHandlerAdapter.support(loginType(null))){
                    authenticationHandlerAdapter.config(login);
                }
            }
        }
        return login;
    }

    /**
     * 获取登录类型
     * @param request
     * @return
     */
    private LoginType loginType(HttpServletRequest request) {
        String loginType = null;
        if(request!=null){
            loginType = WebUtils.findParameterValue(request, "loginType");
        }
        if(StringUtils.isEmpty(loginType)){
            return properties.getLoginType();
        }else{
            return LoginType.valueOf(loginType);
        }
    }

    @Override
    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        initAuthenticationHandlerAdapter(context);
        super.setApplicationContext(context);
    }

    private void initAuthenticationHandlerAdapter(ApplicationContext context) {
        Map<String, AuthenticationHandlerAdapter> handlerAdapterMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, AuthenticationHandlerAdapter.class, true, false);
        if(MapUtils.isNotEmpty(handlerAdapterMap)){
            authenticationHandlerAdapters = new ArrayList<>(handlerAdapterMap.values());
        }
    }

}
