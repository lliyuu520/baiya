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
     * 工厂编码
     * 用于唯一标识一个工厂，通常由系统自动生成
     */
    private String code;

    /**
     * 工厂名称
     * 工厂的显示名称，用于界面展示
     */
    private String name;
    
    /**
     * 父级部门
     */
    private String parentCode;

}
