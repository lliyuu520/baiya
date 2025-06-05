package com.miguoma.by.common.utils;

import lombok.experimental.UtilityClass;

/**
 * 进制转换工具类
 *
 * @author liliangyu
 */
@UtilityClass
public class NumberConvertUtil {

    private static final String BASE_62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 字符串转2进制
     *
     * @param str 输入字符串
     * @return 2进制字符串
     */
    public String toBinary(String str) {
        StringBuilder binary = new StringBuilder();
        for (char c : str.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        return binary.toString();
    }

    /**
     * 2进制转字符串
     *
     * @param binary 2进制字符串
     * @return 原始字符串
     */
    public String fromBinary(String binary) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            String byteStr = binary.substring(i, Math.min(i + 8, binary.length()));
            str.append((char) Integer.parseInt(byteStr, 2));
        }
        return str.toString();
    }

    /**
     * 字符串转10进制 如果输入是纯数字，则直接返回原字符串 否则将每个字符转换为对应的ASCII码值
     *
     * @param str 输入字符串
     * @return 10进制字符串
     */
    public String toDecimal(String str) {
        // 检查是否为纯数字
        if (str.matches("\\d+")) {
            return str;
        }

        // 非纯数字，进行字符编码转换
        StringBuilder decimal = new StringBuilder();
        for (char c : str.toCharArray()) {
            decimal.append((int) c);
        }
        return decimal.toString();
    }

    /**
     * 10进制转字符串
     *
     * @param decimal 10进制字符串
     * @return 原始字符串
     */
    public String fromDecimal(String decimal) {
        StringBuilder str = new StringBuilder();
        // 每3个字符为一组（因为ASCII码最大为127，转换为字符串最多3位）
        for (int i = 0; i < decimal.length(); i += 3) {
            String numStr = decimal.substring(i, Math.min(i + 3, decimal.length()));
            str.append((char) Integer.parseInt(numStr));
        }
        return str.toString();
    }

    /**
     * 字符串转16进制
     *
     * @param str 输入字符串
     * @return 16进制字符串
     */
    public String toHex(String str) {
        StringBuilder hex = new StringBuilder();
        for (char c : str.toCharArray()) {
            hex.append(String.format("%02X", (int) c));
        }
        return hex.toString();
    }

    /**
     * 16进制转字符串
     *
     * @param hex 16进制字符串
     * @return 原始字符串
     */
    public String fromHex(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String byteStr = hex.substring(i, Math.min(i + 2, hex.length()));
            str.append((char) Integer.parseInt(byteStr, 16));
        }
        return str.toString();
    }

    /**
     * 字符串转62进制
     *
     * @param str 输入字符串
     * @return 62进制字符串
     */
    public String toBase62(String str) {
        StringBuilder base62 = new StringBuilder();
        for (char c : str.toCharArray()) {
            int value = (int) c;
            while (value > 0) {
                base62.insert(0, BASE_62_CHARS.charAt(value % 62));
                value /= 62;
            }
            base62.append(" ");
        }
        return base62.toString().trim();
    }

    /**
     * 62进制转字符串
     *
     * @param base62 62进制字符串（空格分隔）
     * @return 原始字符串
     */
    public String fromBase62(String base62) {
        StringBuilder str = new StringBuilder();
        String[] numbers = base62.split(" ");
        for (String number : numbers) {
            int value = 0;
            for (char c : number.toCharArray()) {
                value = value * 62 + BASE_62_CHARS.indexOf(c);
            }
            str.append((char) value);
        }
        return str.toString();
    }
}
