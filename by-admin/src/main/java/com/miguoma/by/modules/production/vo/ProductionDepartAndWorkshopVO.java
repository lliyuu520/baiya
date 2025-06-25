package com.miguoma.by.modules.production.vo;

import com.miguoma.by.modules.production.entity.ProductionDepartAndWorkshop;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 用于前端展示
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionDepartAndWorkshopVO extends ProductionDepartAndWorkshop {
    /** 父级名称 */

    private String parentName;
    /**
     * 父级编码
     */
    private String parentCode;
}
