package com.sucl.smsm.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author sucl
 * @since 2018/12/08
 */
@Configuration
public class I18nConfig {

    @Value(value = "${spring.messages.basename}")
    private String basename;

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(basename);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
