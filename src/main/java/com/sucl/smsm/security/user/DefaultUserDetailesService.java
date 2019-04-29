package com.sucl.smsm.security.user;

import com.sucl.smsm.security.service.UserHolderService;
import com.sucl.smsm.security.service.UserHolderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 通过用户名构建UserDetails
 * @author sucl
 * @date 2019/4/28
 */
@Service
public class DefaultUserDetailesService implements UserDetailsService {
    @Autowired
    private UserHolderService userHolderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IUser user = userHolderService.getUser(username);
        if(user==null) return null;
        AbstractUserDetails userDetails = new AbstractUserDetails(user);
        return userDetails;
    }

    @Configuration
    public static class UserHolderConfiguration{

        @Bean
        public UserHolderService userHolderService(){
            return new UserHolderServiceImpl();
        }
    }
}
