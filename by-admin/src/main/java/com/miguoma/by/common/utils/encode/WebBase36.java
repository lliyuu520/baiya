package com.miguoma.by.common.utils.encode;

/**
 * WebBase62 - 兼容网页风格的Base62编码工具
 * 使用字符集：0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
 */
public class WebBase36 {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = CHARS.length();

    /**
     * 将数字编码为Base62字符串
     *
     * @param num 要编码的数字
     * @return Base62编码后的字符串
     */
    public static String encode(long num) {
        if (num == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, CHARS.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.toString();
    }

    /**
     * 将Base62字符串解码为数字
     *
     * @param str Base62编码的字符串
     * @return 解码后的数字
     * @throws IllegalArgumentException 如果输入字符串包含非法字符
     */
    public static long decode(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        long result = 0;
        for (char c : str.toCharArray()) {
            int value = CHARS.indexOf(c);
            if (value == -1) {
                throw new IllegalArgumentException("Invalid character in input string: " + c);
            }
            result = result * BASE + value;
        }
        return result;
    }
}