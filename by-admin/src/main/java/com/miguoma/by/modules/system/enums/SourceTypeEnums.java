package com.miguoma.by.modules.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 来源类型枚举
 */
@Getter
@AllArgsConstructor
public enum SourceTypeEnums {
    /**
     * 订单
     */
    ORDER("ORDER", "订单"),
    /**
     * 产品
     */
    PRODUCT("PRODUCT", "产品"),

    /**
     * 箱号
     */
    BOX_NO("BOX_NO", "箱号"),
    /**
     * 常量
     */
    CONSTANT("CONSTANT","常量"),
    /**
     * 车间
     */
    WORKSHOP("WORKSHOP", "车间"),
    





    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static SourceTypeEnums getByCode(String code) {
        for (SourceTypeEnums value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
} 
