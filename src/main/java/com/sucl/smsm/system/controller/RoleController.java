package com.sucl.smsm.system.controller;

import com.sucl.smsm.system.entity.Role;
import com.sucl.smsm.system.service.RoleService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sucl.smsm.core.web.BaseController;
import java.util.List;

/**
 * <p>
 *  前端控制�?
 * </p>
 *
 * @author sucl
 * @since 2019-04-30
 */
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController<RoleService,Role> {

}
