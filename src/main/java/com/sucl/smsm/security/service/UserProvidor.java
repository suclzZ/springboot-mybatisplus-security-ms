package com.sucl.smsm.security.service;

import com.sucl.smsm.security.user.IUser;
import org.springframework.core.Ordered;

/**
 * 通过用户名来查找用户
 * @see UserHolderService
 * @author sucl
 * @date 2019/4/28
 */
public interface UserProvidor extends Ordered {

    IUser getUser(String username);

    default int getOrder(){
        return 0;
    }
}
