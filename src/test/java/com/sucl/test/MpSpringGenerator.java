package com.sucl.test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MpSpringGenerator extends BasicTest{
    @Autowired
    private AutoGenerator autoGenerator;

    @Test
    public void test(){
        autoGenerator.execute();
    }
}
