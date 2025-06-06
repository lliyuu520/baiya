package com.miguoma.by.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 产品类型枚举
 *
 * @author liliangyu
 */
@Getter
@AllArgsConstructor
public enum ProductTypeEnum {
    /**
     * 半成品
     */
    SEMI_FINISHED_PRODUCT("SEMI_FINISHED_PRODUCT", "半成品"),

    /**
     * 成品
     */
    FINISHED_PRODUCT("FINISHED_PRODUCT", "成品");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String description;
}