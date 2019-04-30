package com.sucl.smsm.security.service;

import com.sucl.smsm.security.user.IUser;

/**
 * 通过用户名查找用户，适配器，具体实现在UserProvidor
 * 属于一种尝试机制，实现多表用户认证
 * @author sucl
 * @date 2019/4/28
 */
public interface UserHolderService{

    IUser getUser(String username);
}
