package com.miguoma.by.modules.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 来源字段枚举
 */
@Getter
@AllArgsConstructor
public enum SourceFiledEnums {
    /**
     * 限用日期
     */
    LIMITED_USE_DATE("LIMITED_USE_DATE", "限用日期"),

    /**
     * 部门编码
     */
    PRODUCTION_DEPART_CODE("PRODUCTION_DEPART_CODE", "部门编码"),

    /**
     * 车间编码
     */
    PRODUCTION_WORKSHOP_CODE("PRODUCTION_WORKSHOP_CODE", "车间编码"),

    /**
     * 订单编码
     */
    ORDER_CODE("ORDER_CODE", "订单编码"),

    /**
     * 产品编码
     */
    PRODUCT_CODE("PRODUCT_CODE", "产品编码"),

    /**
     * 箱号
     */
    BOX_NO("BOX_NO", "箱号"),

    /**
     * 常量
     */
    CONSTANT("CONSTANT", "常量");

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
    public static SourceFiledEnums getByCode(String code) {
        for (SourceFiledEnums value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
