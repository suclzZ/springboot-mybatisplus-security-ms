package com.sucl.smsm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sucl.smsm.core.ui.TreeNode;
import com.sucl.smsm.security.service.MenuProviderService;
import com.sucl.smsm.security.user.IUser;
import com.sucl.smsm.system.entity.Menu;
import com.sucl.smsm.system.mapper.MenuMapper;
import com.sucl.smsm.system.service.MenuService;
import com.sucl.smsm.util.TreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现�?
 * </p>
 *
 * @author sucl
 * @since 2019-05-10
 */
@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService ,MenuProviderService {

    @Override
    public List<TreeNode> getMenus() {
        List<Menu> menus = baseMapper.selectWithParent();
        return menuToTreenode(menus);
    }

    public List<TreeNode> menuToTreenode(List<Menu> menus){
        List<TreeNode> treeNodes = null;
        if(menus!=null && menus.size()>0){
            treeNodes = new ArrayList<>();
            for(Menu menu : menus){
                treeNodes.add(buildTreenode(menu));
            }
        }
        if(treeNodes!=null){
            return TreeUtils.render(treeNodes);
        }
        return treeNodes;
    }

    private TreeNode buildTreenode(Menu menu) {
        TreeNode treeNode = new TreeNode();
        treeNode.setId(menu.getMenuId());
        treeNode.setName(menu.getMenuName());
        if(menu.getParentMenu()!=null){
            treeNode.setPid(menu.getParentMenu().getMenuId());
        }
        treeNode.setData(menu);
        treeNode.setStyle(menu.getStyle());
        treeNode.setPath(menu.getPath());
        return treeNode;
    }

    @Override
    public String menuHtml(IUser user) {
        return null;
    }

    @Override
    public String menuHtml(IUser user, String expand) {
        return null;
    }

    @Override
    public List<String> fullMenuIds(String path) {
        Menu menu = baseMapper.selectWithParentByPath(path);
        return fullIds(menu);
    }

    private List<String> fullIds(Menu menu) {
        List<String> ids = new ArrayList<>();
        if(menu!=null && menu.getParentMenu()!=null){
            ids.addAll(fullIds(menu.getParentMenu()));
        }
        ids.add(menu.getMenuId());
        return ids;
    }

    @Override
    public List<String> fullMenuNames(String path) {
        Menu menu = baseMapper.selectWithParentByPath(path);
        return fullNames(menu);
    }

    @Override
    public String pageId(String path) {
        Menu menu = baseMapper.selectOne(new QueryWrapper<Menu>().lambda().eq(Menu::getPath, path));
        return menu!=null?menu.getMenuId():null;
    }

    private List<String> fullNames(Menu menu) {
        List<String> names = new ArrayList<>();
        if(menu!=null && menu.getParentMenu()!=null){
            names.addAll(fullNames(menu.getParentMenu()));
        }
        names.add(menu.getMenuName());
        return names;
    }
}
