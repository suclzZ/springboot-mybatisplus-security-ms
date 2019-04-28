package com.sucl.smsm.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author sucl
 * @date 2019/4/28
 */
public class AbstractUserDetails extends User {

    public AbstractUserDetails(IUser iu){
        super(iu.getUsername(),iu.getPassword(),iu.authorities());
    }

    public AbstractUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AbstractUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
