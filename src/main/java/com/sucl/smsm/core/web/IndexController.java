package com.sucl.smsm.core.web;

import com.sucl.smsm.core.dto.TreeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 *
 */
@Controller
@RequestMapping
public class IndexController {

    private static final String PAGE_PREFIX = "views/";

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = {"/login","/formlogin"})
    public String login(){
        return "login";
    }

    /**
     * 主页
     * @param map
     * @return
     */
    @RequestMapping(value = {"/index",""})
    public String index(Map<String,Object> map){
        return "index";
    }

    /**
     * 登出
     * @param map
     * @return
     */
    @RequestMapping("/logout")
    public String logout(Map<String,Object> map){
        return "login";
    }

    /**
     * 图标库
     * @param map
     * @return
     */
    @RequestMapping("/icons")
    public String icons(Map<String,Object> map){
        return "icons";
    }

    /**
     * 页面分发，菜单导航
     * 注意 请求匹配问题
     * @param pager
     * @param map
     * @return
     */
    @RequestMapping(value = "/{pager}",method = RequestMethod.GET,produces = "text/html")//
    public String pageDispatcher(@PathVariable("pager") String pager,Map<String,Object> map){
//        pager = TreeUtils.pageDecode(pager);
//        map.put("menu",menuProviderService.menuHtml(null,pager));
//        Map<String,String> pnMap = sysMenuService.getPathNameMap();
//        map.put("menuText",pnMap.get(pager));
        return PAGE_PREFIX+pager;
    }
}
