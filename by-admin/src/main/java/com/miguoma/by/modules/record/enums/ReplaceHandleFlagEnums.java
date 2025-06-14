package com.miguoma.by.modules.record.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 置换状态
 *
 * @author liliangyu
 */
@Getter
@AllArgsConstructor
public enum ReplaceHandleFlagEnums {
    /**
     *  待处理
     */
    WAITING("WAITING", "待处理"),
    /**
     * 成功
     */
    SUCCESS("SUCCESS", "成功"),
    /**
     * 失败
     */
    FAIL("FAIL", "失败");
    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;
}
