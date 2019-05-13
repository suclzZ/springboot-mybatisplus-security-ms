package com.sucl.smsm.system.controller;

import com.sucl.smsm.core.ui.TreeNode;
import com.sucl.smsm.system.entity.Menu;
import com.sucl.smsm.system.service.MenuService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sucl.smsm.core.web.AbstractBaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sucl
 * @since 2019-05-10
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends AbstractBaseController<MenuService,Menu> {

    @GetMapping("/tree")
    public List<TreeNode> getMenuTrees(){
        return service.getMenus();
    }

}
