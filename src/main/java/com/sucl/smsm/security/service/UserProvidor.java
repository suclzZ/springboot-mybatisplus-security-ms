package com.sucl.smsm.security.service;

import com.sucl.smsm.security.user.IUser;
import org.springframework.core.Ordered;

/**
 * 通过用户名来查找用户
 * @author sucl
 * @date 2019/4/28
 */
public interface UserProvidor extends Ordered {

    boolean support(Object o);

    IUser getUser(String username);
}
