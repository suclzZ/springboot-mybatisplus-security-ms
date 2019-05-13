package com.sucl.smsm.security.auth.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author sucl
 * @date 2019/5/5
 */
@Configuration
public class JwtAuthConfig {

    @Bean
    public JwtTokenHelper jwtHelper(){
        return new JwtTokenHelper();
    }

    @Bean
    @ConditionalOnProperty(name = "jwt",prefix = "web.security")
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return new User(username,null,null);
            }
        },jwtHelper());
    }
}
