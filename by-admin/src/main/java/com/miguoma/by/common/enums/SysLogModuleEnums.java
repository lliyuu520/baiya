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
     * 用户
     */
    USER("用户"),
    /**
     * 角色
     */
    ROLE("角色"),
    /**
     * 菜单
     */
    MENU("菜单"),
    /**
     * 工厂
     */
    FACTORY("工厂"),
    
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
     * 班组
     */
    TEAM("班组"),

    /**
     * 班次
     */
    SHIFT("班次"),
    /**
     * 订单
     */
    ORDER("订单"),
    /**
     * 编码规则
     */
    CODE_RULE("编码规则"),
    /**
     * 采集软件
     */
    CLIENT("采集软件"),
    /**
     * PDA
     */
    PDA("PDA"),
    /**
     * 箱物流码
     */
    RECORD_BOX_CODE("箱物流码"),

    /**
     * 袋物流码
     */
    RECORD_BAG_CODE("袋物流码"),

    /**
     * 二维码
     */
    RECORD_QR_CODE("袋二维码"),
                    
    /**
     * 二维码置换记录
     */
    RECORD_QR_CODE_REPLACE("二维码置换记录"),
    
    /**
     * 箱物流码置换记录
     */
    RECORD_BOX_CODE_REPLACE("箱物流码置换记录"),

    /**
     * APK
     */
    APK("APK"),
    


    ;

    private final String desc;
}
