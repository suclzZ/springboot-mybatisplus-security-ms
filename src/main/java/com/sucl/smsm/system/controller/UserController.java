package com.sucl.smsm.system.controller;

import com.sucl.smsm.system.entity.User;
import com.sucl.smsm.system.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sucl.smsm.core.web.AbstractBaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sucl
 * @since 2018-12-08
 */
@RestController
@RequestMapping("/system/user")
public class UserController extends AbstractBaseController<UserService,User> {

}
