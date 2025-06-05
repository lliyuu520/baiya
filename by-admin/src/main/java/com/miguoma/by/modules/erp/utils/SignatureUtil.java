package com.miguoma.by.modules.erp.utils;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.miguoma.by.modules.erp.constant.SignatureConstants;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 签名工具类
 *
 * @author AI Assistant
 */
@Slf4j
public class SignatureUtil {

    /**
     * 生成签名
     *
     * @param appId     应用ID
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param appSecret 应用密钥
     * @return 签名
     */
    public static String generateSignature(String appId, String timestamp, String nonce,
                                           String appSecret) {
        try {
            // 构建待签名字符串：appId + timestamp + nonce + requestBody
            StringBuilder signString = new StringBuilder();
            signString.append(appId != null ? appId : "");
            signString.append(timestamp != null ? timestamp : "");
            signString.append(nonce != null ? nonce : "");
            log.debug("signString:{}", signString);
            // 使用HMAC-SHA256生成签名
            HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, appSecret.getBytes(StandardCharsets.UTF_8));
            final String string = signString.toString();
            String signature = hMac.digestHex(string);

            log.debug("生成签名: {}", signature);
            return signature;
        } catch (Exception e) {
            log.error("签名生成失败", e);
            throw new RuntimeException("签名生成失败", e);
        }
    }

    /**
     * 验证签名
     *
     * @param appId     应用ID
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param signature 待验证的签名
     * @param appSecret 应用密钥
     * @return 验证结果
     */
    public static boolean verifySignature(String appId, String timestamp, String nonce,
                                          String signature, String appSecret) {
        try {
            String expectedSignature = generateSignature(appId, timestamp, nonce, appSecret);
            boolean result = expectedSignature.equals(signature);
            log.debug("签名验证结果: {}, 期望签名: {}, 实际签名: {}", result, expectedSignature, signature);
            return result;
        } catch (Exception e) {
            log.error("签名验证失败", e);
            return false;
        }
    }

    /**
     * 验证时间戳是否有效
     *
     * @param timestamp 时间戳字符串
     * @return 是否有效
     */
    public static boolean isValidTimestamp(String timestamp) {
        try {
            long ts = Long.parseLong(timestamp);
            long currentTime = System.currentTimeMillis();
            long diff = Math.abs(currentTime - ts);

            boolean valid = diff <= SignatureConstants.SIGNATURE_EXPIRE_TIME;
            log.debug("时间戳验证结果: {}, 时间差: {} ms", valid, diff);
            return valid;
        } catch (NumberFormatException e) {
            log.warn("时间戳格式错误: {}", timestamp);
            return false;
        }
    }
}
