package com.baiya.common.utils;

import cn.hutool.core.lang.Assert;
import com.miguoma.by.common.utils.WebBase62;
import org.junit.jupiter.api.Test;

/**
 * StringEncoder 单元测试
 */
class StringEncoderTest {

    @Test
    void testCalculateBase62Length() {
        
        final String encode = WebBase62.encode(20250530L);
      
        
        //期望得到 1mY5s
        Assert.equals("1mY5s",encode);
        
    }
}