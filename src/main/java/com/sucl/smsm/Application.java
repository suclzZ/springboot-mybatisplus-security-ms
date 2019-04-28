package com.sucl.smsm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author sucl
 * @since 2018/12/8
 */
@SpringBootApplication
@MapperScan("com.sucl.smsm.*.mapper")
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
