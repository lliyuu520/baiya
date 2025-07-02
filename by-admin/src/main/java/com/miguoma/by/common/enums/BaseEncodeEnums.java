package com.miguoma.by.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  基础编码枚举
 */
@Getter
@AllArgsConstructor
public enum BaseEncodeEnums {
    /**
     * 10进制
     */
    BASE_10("BASE_10","10进制"),
    /**
     * 16进制
     */
        BASE_16("BASE_16","16进制"),
    /**
     * 26进制
     */
    BASE_26("BASE_26","26进制"),
    /**
     * 36进制
     */
    BASE_36("BASE_36","36进制"),
    /**
     * 52进制
     */
    BASE_52("BASE_52","52进制"),
    /**
     * 62进制
     */
    BASE_62("BASE_62", "62进制");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
}
