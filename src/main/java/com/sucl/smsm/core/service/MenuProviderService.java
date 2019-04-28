package com.sucl.smsm.core.service;


import com.sucl.smsm.security.user.IUser;

/**
 *
 *
 */
public interface MenuProviderService {

    String menuHtml(IUser user);

    String menuHtml(IUser user, String expand);

}
