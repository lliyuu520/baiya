package com.miguoma.by.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统日志模块枚举
 *
 * @author liliangyu
 */
@Getter
@AllArgsConstructor
public enum SysLogModuleEnums {
    /**
     * 用户管理
     */
    USER("用户管理"),
    /**
     * 角色管理
     */
    ROLE("角色管理"),
    /**
     * 菜单管理
     */
    MENU("菜单管理"),
    /**
     * 工厂管理
     */
    FACTORY("工厂管理"),
    /**
     * 车间管理
     */
    WORK_SHOP("车间管理"),
    /**
     * 产线管理
     */
    PRODUCTION_LINE("产线管理"),
    /**
     * 机台管理
     */
    MACHINE("机台管理"),
    /**
     * 产品管理
     */
    PRODUCT("产品管理"),
    /**
     * 产品分类管理
     */
    PRODUCT_CATEGORY("产品分类管理"),
    /**
     * 班组管理
     */
    TEAM("班组管理"),
    /**
     * 订单管理
     */
    ORDER("订单管理"),
    /**
     * 编码规则管理
     */
    CODE_RULE("编码规则管理"),
    /**
     * 采集软件
     */
    CLIENT("采集软件"),
    /**
     * 二维码关联
     */
    QR_CODE_CORRELATION("二维码关联"),
    ;

    private final String desc;
}
