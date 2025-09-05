package com.xlproject.modules.entrance;

import com.xlproject.modules.service.Generator.BizNoGenerator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BizNoTest {
    @Autowired
    BizNoGenerator bizNoGenerator;


    @Test
    void test01(){
        System.out.println(bizNoGenerator.generate("RK"));
    }
    @Test
    void test02(){

    }
}
