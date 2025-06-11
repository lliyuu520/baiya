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
public enum ErpLogModuleEnums {

    
    /**
     * 车间
     */
    DEPART_AND_WORK_SHOP("部门&车间"),

    /**
     * 产品
     */
    PRODUCT("产品"),
    /**
     * 产品分类
     */
    PRODUCT_CATEGORY("产品分类"),

    /**
     * 订单
     */
    ORDER("订单"),


    ;

    private final String desc;
}
