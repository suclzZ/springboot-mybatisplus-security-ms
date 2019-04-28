package com.sucl.smsm.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/28
 */
public interface IUser {

    String getUsername();

    String getPassword();

    List<String> getRoles();

    default Collection<? extends GrantedAuthority> authorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (getRoles()!=null){
            for(String roleId : getRoles()){
                GrantedAuthority authority = new SimpleGrantedAuthority(roleId);
                authorities.add(authority);
            }
        }
        return authorities;
    }
}
