package com.sucl.test.mapper;

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
        //BaseMapper
        User user = userMapper.selectById(1);
        System.out.println("user : "+user);
        //ActiveRecord
//        List<User> users = user.selectAll();
//        System.out.println("users : "+users);
        //mapper.xml 不能同时使用
//        users = userMapper.getAllUsers();
//        System.out.println("users :" +users);
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
