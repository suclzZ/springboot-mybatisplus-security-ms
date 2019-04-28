package com.sucl.smsm.security.service;

import com.sucl.smsm.security.user.IUser;

/**
 * @author sucl
 * @date 2019/4/28
 */
public interface UserHolderService{

    IUser getUser(String username);
}
