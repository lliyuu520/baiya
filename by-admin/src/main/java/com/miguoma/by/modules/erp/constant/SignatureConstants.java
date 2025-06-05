package com.miguoma.by.modules.erp.constant;

/**
 * 签名相关常量
 *
 * @author AI Assistant
 */
public class SignatureConstants {



    /**
     * 请求头 - 应用ID
     */
    public static final String HEADER_APP_ID = "X-App-Id";

    /**
     * 请求头 - 时间戳
     */
    public static final String HEADER_TIMESTAMP = "X-Timestamp";

    /**
     * 请求头 - 随机数
     */
    public static final String HEADER_NONCE = "X-Nonce";

    /**
     * 请求头 - 签名
     */
    public static final String HEADER_SIGNATURE = "X-Signature";

    /**
     * 签名有效期（毫秒）- 5分钟
     */
    public static final long SIGNATURE_EXPIRE_TIME = 5 * 60 * 1000L;

    /**
     * 签名验证失败错误码
     */
    public static final int SIGNATURE_ERROR_CODE = 4001;

    /**
     * 时间戳过期错误码
     */
    public static final int TIMESTAMP_EXPIRED_CODE = 4002;

    /**
     * 应用ID无效错误码
     */
    public static final int INVALID_APP_ID_CODE = 4003;
}