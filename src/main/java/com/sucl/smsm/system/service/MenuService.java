package com.sucl.smsm.system.service;

import com.sucl.smsm.core.ui.TreeNode;
import com.sucl.smsm.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务�?
 * </p>
 *
 * @author sucl
 * @since 2019-05-10
 */
public interface MenuService extends IService<Menu> {

    List<TreeNode> getMenus();

}
