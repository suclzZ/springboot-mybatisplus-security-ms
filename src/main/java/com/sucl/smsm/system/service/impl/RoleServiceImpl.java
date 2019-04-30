package com.sucl.smsm.system.service.impl;

import com.sucl.smsm.system.entity.Role;
import com.sucl.smsm.system.mapper.RoleMapper;
import com.sucl.smsm.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现�?
 * </p>
 *
 * @author sucl
 * @since 2019-04-30
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
