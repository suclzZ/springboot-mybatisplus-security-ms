package com.sucl.smsm.core.service.impl;

import com.sucl.smsm.core.dto.Node;
import com.sucl.smsm.core.dto.TreeUtils;
import com.sucl.smsm.core.service.MenuProviderService;
import com.sucl.smsm.security.user.IUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取菜单
 *
 */
@Service
public class MenuProviderServiceImpl implements MenuProviderService {

    @Override
    public String menuHtml(IUser user) {
        List<Node> menuNodes = null;
        return TreeUtils.buildMenuHtml(menuNodes);
    }

    @Override
    public String menuHtml(IUser user,String expand) {
        List<Node> menuNodes = null;
        return TreeUtils.buildMenuHtml(menuNodes,expand);
    }


}
