package com.sucl.smsm.core.service;

import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author sucl
 * @since 2018/12/8
 */
@Service
public class DefaultIDbQuery implements ApplicationContextAware {
    private String databaseId;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initDataBaseType(applicationContext);
    }

    private void initDataBaseType(ApplicationContext applicationContext) {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        if(dataSource!=null){
            VendorDatabaseIdProvider vendorDatabaseIdProvider = new VendorDatabaseIdProvider();
            this.databaseId = vendorDatabaseIdProvider.getDatabaseId(dataSource);
        }
    }
}
