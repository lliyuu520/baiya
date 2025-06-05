package com.miguoma.by.modules.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author liliangyu
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    /**
     * 管理员
     */
    ADMIN("ADMIN", "管理员"),

    /**
     * 客户端用户
     */
    CLIENT("CLIENT", "客户端用户");

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
    public static UserTypeEnum getByCode(String code) {
        for (UserTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
} 
