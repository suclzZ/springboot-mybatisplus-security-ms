package com.sucl.smsm.core.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sucl
 * @since 2018/12/08
 */
@Configuration
public class MybatisConfiguration {
    /***
     * plus 的性能优化,开发模式使用
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active",havingValue = "dev")
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1000);//sql 最大执行时长
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}
