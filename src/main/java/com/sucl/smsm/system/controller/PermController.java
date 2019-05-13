package com.sucl.smsm.system.controller;

import com.sucl.smsm.system.entity.Perm;
import com.sucl.smsm.system.service.PermService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sucl.smsm.core.web.AbstractBaseController;

/**
 * <p>
 *  前端控制�?
 * </p>
 *
 * @author sucl
 * @since 2019-04-30
 */
@RestController
@RequestMapping("/system/perm")
public class PermController extends AbstractBaseController<PermService,Perm> {

}
