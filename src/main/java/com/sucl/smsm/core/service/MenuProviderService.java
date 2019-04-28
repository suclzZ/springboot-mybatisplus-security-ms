package com.sucl.smsm.core.service;


//import com.sucl.smsm.security.user.IUser;

/**
 *
 *
 */
public interface MenuProviderService {

    String menuHtml(Object user);

    String menuHtml(Object user, String expand);

}
