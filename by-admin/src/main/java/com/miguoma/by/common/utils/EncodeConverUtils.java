package com.miguoma.by.common.utils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import lombok.experimental.UtilityClass;

/**
 * 进制转换工具
 * 支持 BASE_10、BASE_16、BASE_26、BASE_32、BASE_36、BASE_52、BASE_58、BASE_62
 * 入参：number int类型，toBase 形如 'BASE_10' 的字符串
 * 出参：字符串
 *
 * 用法示例：
 * BaseConverUtils.convert(12345, "BASE_36");
 */
@UtilityClass
public class EncodeConverUtils {

   public static final String BASE_10 = "BASE_10";
   
   public static final String BASE_16 = "BASE_16";
   // 小写字母
   public static final String BASE_26 = "BASE_26";
   // 数字+小写字母
   public static final String BASE_36 = "BASE_36";
   // 小写字母+大写字母
   public static final String BASE_52 = "BASE_52";
   // 数字+小写字母+大写字母
   public static final String BASE_62 = "BASE_62";

   private static final Map<String, Integer> BASE_MAP = new HashMap<>();
   private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   static {
      BASE_MAP.put(BASE_10, 10);
      BASE_MAP.put(BASE_16, 16);
      BASE_MAP.put(BASE_26, 26);
      BASE_MAP.put(BASE_36, 36);
      BASE_MAP.put(BASE_52, 52);
      BASE_MAP.put(BASE_62, 62);
   }

   /**
    * 进制转换（源进制固定为10）
    * 
    * @param number int类型数字
    * @param toBase 目标进制字符串，如 "BASE_36"
    * @return 目标进制字符串
    */
   public static String convert(int number, String toBase) {
      int toRadix = getRadix(toBase);
      if (toRadix <= 36) {
         return Integer.toString(number, toRadix).toUpperCase();
      } else {
         return encodeCustom(BigInteger.valueOf(number), toRadix);
      }
   }

   private static int getRadix(String baseStr) {
      Integer radix = BASE_MAP.get(baseStr);
      if (radix == null)
         throw new IllegalArgumentException("不支持的进制类型: " + baseStr);
      if (radix < 2 || radix > 62)
         throw new IllegalArgumentException("进制范围仅支持2~62");
      return radix;
   }

   // 仅用于37~62进制
   private static String encodeCustom(BigInteger value, int radix) {
      if (value.compareTo(BigInteger.ZERO) == 0)
         return "0";
      StringBuilder sb = new StringBuilder();
      BigInteger base = BigInteger.valueOf(radix);
      BigInteger n = value;
      while (n.compareTo(BigInteger.ZERO) > 0) {
         int idx = n.mod(base).intValue();
         sb.insert(0, DIGITS.charAt(idx));
         n = n.divide(base);
      }
      return sb.toString();
   }
}
