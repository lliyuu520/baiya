package com.miguoma.by.modules.production.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生产部门&车间
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionDepartAndWorkshop extends BaseEntity {
    /**
     * 生产部门&车间编码
     * 用于唯一标识一个生产部门&车间，通常由系统自动生成
     */
    private String code;

    /**
     * 生产部门&车间名称
     * 生产部门&车间的显示名称，用于界面展示
     */
    private String name;

   
    
    /**
     * 父级编码ID
     */
    private String parentCode;
     /**
     * 别名
     */
    private String alias;

    /**
     * 编码规则ID
     */
    private  Long  codeRuleId;

    
}
