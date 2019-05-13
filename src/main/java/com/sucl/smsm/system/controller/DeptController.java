package com.sucl.smsm.system.controller;

import com.sucl.smsm.system.entity.Dept;
import com.sucl.smsm.system.service.DeptService;

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
@RequestMapping("/system/dept")
public class DeptController extends AbstractBaseController<DeptService,Dept> {

}
