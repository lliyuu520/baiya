package com.miguoma.by.modules.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 编码方式枚举
 *
 * @author liliangyu
 */
@Getter
@AllArgsConstructor
public enum EncodeTypeEnums {
 
    /**
     * Base36
     */
    BASE_36("BASE_36", "BASE_36"),
    /**
     * Base62
     */
    BASE_62("BASE_62", "BASE_62"),
    ;

    /**
     * 编码方式
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
}
