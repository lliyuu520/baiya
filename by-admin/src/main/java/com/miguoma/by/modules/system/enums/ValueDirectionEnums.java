package com.miguoma.by.modules.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 取值方向枚举
 */
@Getter
@AllArgsConstructor
public enum ValueDirectionEnums {

    /**
     * 左
     */
    LEFT("LEFT", "左"),

    /**
     * 右
     */
    RIGHT("RIGHT", "右");

    /**
     * 方向值
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
}

  
