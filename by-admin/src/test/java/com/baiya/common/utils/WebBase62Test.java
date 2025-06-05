package com.baiya.common.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.miguoma.by.common.utils.WebBase62;

class WebBase62Test {

    @Test
    @DisplayName("测试基本编码和解码")
    void testBasicEncodeDecode() {
        // 测试用例
        long[] testCases = {
                0L,
                1L,
                62L,
                63L,
                123456789L,
                Long.MAX_VALUE
        };

        for (long num : testCases) {
            String encoded = WebBase62.encode(num);
            long decoded = WebBase62.decode(encoded);
            assertEquals(num, decoded, "编码解码应该保持一致");
            System.out.printf("数字: %d%n", num);
            System.out.printf("编码结果: %s%n", encoded);
            System.out.printf("解码结果: %d%n", decoded);
            System.out.println();
        }
    }

    @Test
    @DisplayName("测试边界情况")
    void testEdgeCases() {
        // 测试null值
        assertThrows(IllegalArgumentException.class, () -> {
            WebBase62.decode(null);
        }, "null值应该抛出IllegalArgumentException");

        // 测试空字符串
        assertThrows(IllegalArgumentException.class, () -> {
            WebBase62.decode("");
        }, "空字符串应该抛出IllegalArgumentException");

        // 测试非法字符
        assertThrows(IllegalArgumentException.class, () -> {
            WebBase62.decode("abc!");
        }, "包含非法字符的字符串应该抛出IllegalArgumentException");
    }

    @Test
    @DisplayName("测试日期数字编码")
    void testDateNumberEncoding() {
        // 测试日期数字
        long dateNum = 20250530L;
        String encoded = WebBase62.encode(dateNum);
        long decoded = WebBase62.decode(encoded);
        assertEquals(dateNum, decoded, "日期数字编码解码应该保持一致");
        System.out.printf("日期数字: %d%n", dateNum);
        System.out.printf("编码结果: %s%n", encoded);
        System.out.printf("解码结果: %d%n", decoded);
    }
}