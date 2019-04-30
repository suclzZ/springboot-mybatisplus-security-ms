package com.sucl.smsm.system.service.impl;

import com.sucl.smsm.system.entity.Perm;
import com.sucl.smsm.system.mapper.PermMapper;
import com.sucl.smsm.system.service.PermService;
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
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements PermService {

}
