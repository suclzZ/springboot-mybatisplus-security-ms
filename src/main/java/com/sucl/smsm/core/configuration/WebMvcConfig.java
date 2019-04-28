package com.sucl.smsm.core.configuration;

import com.sucl.smsm.core.support.method.ConditionHandlerMethodArgumentResolver;
import com.sucl.smsm.core.support.method.PageHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * @author sucl
 * @since 2018/12/08
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加mvc参数处理器
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ConditionHandlerMethodArgumentResolver());
        argumentResolvers.add(new PageHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 设置默认本地化
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.getDefault());
        return localeResolver;
    }

    /**
     * 国际化相关
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor changeInterceptor = new LocaleChangeInterceptor();
        changeInterceptor.setParamName("lang");
        return changeInterceptor;
    }

    /**
     * 国际化相关
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
