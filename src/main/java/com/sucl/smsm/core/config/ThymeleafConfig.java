package com.sucl.smsm.core.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * @author sucl
 * @date 2019/4/29
 */
@Configuration
public class ThymeleafConfig {

    /**
     * thymeleaf 错误页面处理
     * @return
     */
//    @Bean
    public EmbeddedServletContainerCustomizer customErrorView(){
        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "error/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "error/404.html");
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "error/403.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "error/500.html");
            container.addErrorPages();
        });
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();thymeleafViewResolver.setContentType("text/html;charset=UTF-8");
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        thymeleafViewResolver.setTemplateEngine(templateEngine);
        return thymeleafViewResolver;
    }

}
