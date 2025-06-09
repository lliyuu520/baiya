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
    FINISHED_LIMITED_USE_DATE("FINISHED_LIMITED_USE_DATE", "成品限用日期"),

    /**
     * 成品部门编码
     */
    FINISHED_PRODUCTION_DEPART_CODE("FINISHED_PRODUCTION_DEPART_CODE", "成品部门编码"),

    /**
     * 成品车间编码
     */
    FINISHED_PRODUCTION_WORKSHOP_CODE("FINISHED_PRODUCTION_WORKSHOP_CODE", "成品车间编码"),

    /**
     * 成品订单编码
     */
    FINISHED_ORDER_CODE("FINISHED_ORDER_CODE", "成品订单编码"),

    /**
     * 成品产品编码
     */
    FINISHED_PRODUCT_CODE("FINISHED_PRODUCT_CODE", "成品产品编码"),

    //---------------半成品区
    /**
     * 半成品限用日期
     */
    SEMI_FINISHED_LIMITED_USE_DATE("SEMI_FINISHED_LIMITED_USE_DATE", "半成品限用日期"),


    /**
     * 半成品成品部门编码
     */
    SEMI_FINISHED_PRODUCTION_DEPART_CODE("SEMI_FINISHED_PRODUCTION_DEPART_CODE", "半成品部门编码"),


    /**
     * 半成品车间编码
     */
    SEMI_FINISHED_PRODUCTION_WORKSHOP_CODE("SEMI_FINISHED_PRODUCTION_WORKSHOP_CODE", "半成品车间编码"),



    /**
     * 半成品订单编码
     */
    SEMI_FINISHED_ORDER_CODE("SEMI_FINISHED_ORDER_CODE", "半成品订单编码"),

    /**
     * 半成品产品编码
     */
    SEMI_FINISHED_PRODUCT_CODE("SEMI_FINISHED_PRODUCT_CODE", "半成品产品编码"),



    /**
     * 箱号
     */
    BOX_NO("BOX_NO", "箱号"),

    /**
     * 指定箱号
     */
    SPECIFY_BOX_NO("SPECIFY_BOX_NO", "指定箱号"),

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
