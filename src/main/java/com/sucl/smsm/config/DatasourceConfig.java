package com.sucl.smsm.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * @author sucl
 * @since 2018/12/8
 */
@Configuration
@EnableConfigurationProperties(MsProperties.class)
public class DatasourceConfig {

    /**
     * 业务数据源
     * @return
     */
    @Bean("dataSource")
    @ConfigurationProperties(prefix = "mp.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 事务管理（可以不写）
     * @return
     */
    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 事务隔离级别（默认 ）
     * @return
     */
    @Bean("defaultTransDefinition")
    public DefaultTransactionDefinition defaultTransactionDefinition(){
        return new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
    }

}
