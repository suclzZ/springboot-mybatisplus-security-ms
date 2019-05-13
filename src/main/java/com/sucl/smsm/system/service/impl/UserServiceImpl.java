package com.sucl.smsm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sucl.smsm.security.service.UserProvidor;
import com.sucl.smsm.security.user.IUser;
import com.sucl.smsm.system.entity.User;
import com.sucl.smsm.system.mapper.UserMapper;
import com.sucl.smsm.system.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService ,UserProvidor {

    @Override
    public IUser getUser(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername,username));
    }
}
