package com.miguoma.by.modules.production.strategy;

import cn.hutool.core.util.StrUtil;

import java.security.SecureRandom;

/**
 * 随机字符串生成工具类
 * 支持类型：NUMBER、UPPER_CASE_LETTER、LOWER_CASE_LETTER、NUMBER_UPPER_CASE_LETTER、NUMBER_LOWER_CASE_LETTER、NUMBER_UPPER_CASE_LETTER_LOWER_CASE_LETTER
 */
public class RandomStringUtil {
    private static final String NUMBER = "0123456789";
    private static final String UPPER_CASE_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE_LETTER = "abcdefghijklmnopqrstuvwxyz";

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成指定类型和长度的随机字符串
     * 
     * @param length     长度
     * @param randomType 类型（如NUMBER、UPPER_CASE_LETTER等）
     * @return 随机字符串
     */
    public static String generate(String randomType, int length) {
        if (length == 0 || StrUtil.isBlank(randomType)) {
            return "";
        }
        StringBuilder charPool = new StringBuilder();
        switch (randomType) {
            case "NUMBER":
                charPool.append(NUMBER);
                break;
            case "UPPER_CASE_LETTER":
                charPool.append(UPPER_CASE_LETTER);
                break;
            case "LOWER_CASE_LETTER":
                charPool.append(LOWER_CASE_LETTER);
                break;
            case "NUMBER_UPPER_CASE_LETTER":
                charPool.append(NUMBER).append(UPPER_CASE_LETTER);
                break;
            case "NUMBER_LOWER_CASE_LETTER":
                charPool.append(NUMBER).append(LOWER_CASE_LETTER);
                break;
            case "NUMBER_UPPER_CASE_LETTER_LOWER_CASE_LETTER":
                charPool.append(NUMBER).append(UPPER_CASE_LETTER).append(LOWER_CASE_LETTER);
                break;
            default:
                throw new IllegalArgumentException("不支持的randomType类型: " + randomType);
        }
        String pool = charPool.toString();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = RANDOM.nextInt(pool.length());
            result.append(pool.charAt(idx));
        }
        return result.toString();
    }
}