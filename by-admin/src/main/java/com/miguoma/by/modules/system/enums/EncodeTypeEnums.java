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
public enum EncodeTypeEnums  {
/**
     * Base10
     */
    BASE_10("BASE_10", "Base10"),
    /**
     * Base36
     */
    BASE_36 ("BASE_36", "Base36"),
    /**
     * Base62
     */
    BASE_62("BASE_62", "Base62"),
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
