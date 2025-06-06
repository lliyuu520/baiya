package com.baiya.common.utils;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.codec.Base62Codec;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ByteUtil;
import com.miguoma.by.common.utils.encode.WebBase62;
import org.junit.jupiter.api.Test;

/**
 * StringEncoder 单元测试
 */
class StringEncoderTest {

    @Test
    void testCalculateBase62Length() {
        final String encode = WebBase62.encode(20250530L);
        final Base62Codec base62Codec = Base62Codec.INSTANCE;
        final byte[] bytes = ByteUtil.longToBytes(20250530L);
        final String encode2 = Base62.encode(bytes);
        final byte[] encode1 = base62Codec.encode(bytes);
        final String s = new String(encode1);
        // 转字符串

        //期望得到 1mY5s
        Assert.equals("1mY5s",encode);
        Assert.equals("1mY5s",s);

    }
}