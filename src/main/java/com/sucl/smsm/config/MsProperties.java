package com.sucl.smsm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sucl
 * @date 2019/4/28
 */
@Data
@ConfigurationProperties(prefix = "mp.datasource")
public class MsProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
