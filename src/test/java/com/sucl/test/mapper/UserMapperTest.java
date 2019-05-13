package com.sucl.test.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucl.smsm.system.entity.User;
import com.sucl.smsm.system.mapper.UserMapper;
import com.sucl.test.BasicTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.annotation.Resource;

public class UserMapperTest extends BasicTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void test(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.lambda().like(User::getUsername,"TOM");
//        queryWrapper.lambda().nested()
        queryWrapper.lambda().and(qw->{
            return qw.or(qw1->{
                return qw1.eq(User::getUsername,"JACK");
            }).or(qw2->{
                return qw2.eq(User::getAge,"25");
            });
        });
        userMapper.selectList(queryWrapper);
    }

    @Before
    public void before(){
        System.out.println("before");
    }

    @BeforeClass
    public static void beforeClass(){
        System.out.println("beforeclass");
    }
}
