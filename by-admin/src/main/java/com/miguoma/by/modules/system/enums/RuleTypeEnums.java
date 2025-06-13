package com.miguoma.by.modules.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 规则类型枚举
 */
@Getter
@AllArgsConstructor
public enum RuleTypeEnums {

    /**
     * 箱码
     */
    BOX("BOX_CODE", "箱码"),
    /**
     * 内箱码
     */
    INNER_BOX("INNER_BOX_CODE", "内箱码"),

    /**
     * 袋码
     */
    BAG("BAG_CODE", "袋码"),

    /**
     * 万用码
     */
    UNIVERSAL_CODE("UNIVERSAL_CODE", "万用码"),
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
    public static RuleTypeEnums getByCode(String code) {
        for (RuleTypeEnums value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
    
} 
