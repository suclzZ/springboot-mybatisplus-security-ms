package com.sucl.smsm.system.service.impl;

import com.sucl.smsm.system.entity.Dept;
import com.sucl.smsm.system.mapper.DeptMapper;
import com.sucl.smsm.system.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sucl
 * @since 2018-12-08
 */
@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

}
