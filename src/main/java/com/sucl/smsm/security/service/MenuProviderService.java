package com.sucl.smsm.security.service;

import com.sucl.smsm.security.user.IUser;

import java.util.List;

/**
 *
 *
 */
public interface MenuProviderService {

    String menuHtml(IUser user);

    String menuHtml(IUser user, String expand);

    List<String> fullMenuIds(String path);

    List<String> fullMenuNames(String path);

    String pageId(String path);
}
