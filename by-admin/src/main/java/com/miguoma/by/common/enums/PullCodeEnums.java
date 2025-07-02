package com.miguoma.by.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 码类型枚举
 */
@Getter
@AllArgsConstructor
public enum PullCodeEnums {

    /**
     * 二维码
     */
    QR_CODE("QR_CODE","二维码"),

    /**
     * 物流码
     */
    LOGISTICS_CODE("LOGISTICS_CODE","物流码");

    /**
     * 码类型
     */
    private final String code;

    /**
     * 码类型描述
     */     
    private final String desc;

}
