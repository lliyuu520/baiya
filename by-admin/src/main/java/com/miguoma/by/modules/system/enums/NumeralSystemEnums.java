package com.miguoma.by.modules.system.enums;

import lombok.Getter;

/**
 * 进制枚举
 */
@Getter
public enum NumeralSystemEnums {

    /**
     * 默认进制
     */
    DEFAULT("0", "默认"),

    /**
     * 十进制
     */
    DECIMAL("10", "十进制"),

    /**
     * 十六进制
     */
    HEXADECIMAL("16", "十六进制"),

    /**
     * 三十二进制
     */
    BASE32("32", "三十二进制"),

    /**
     * 六十四进制
     */
    BASE64("64", "六十四进制");

    /**
     * 进制值
     */
    private final String value;

    /**
     * 描述
     */
    private final String description;

    NumeralSystemEnums(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
