package com.miguoma.by.modules.production.vo;

import com.miguoma.by.modules.production.entity.ProductionOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生产订单视图对象
 * 用于前端展示
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionOrderVO extends ProductionOrder {
    /**
     * 限用日期
     */
    private String limitedUseDateStr;
    /**
     * 生产批次
     */
    private String productionBatchNo;
    /**
     * 生产工厂
     */
    private String productionFactoryName;

    /**
     * 生产部门名称
     */
    private String productionDepartName;

    /**
     * 生产车间名称
     */
    private String productionWorkshopName;
    /**
     * 生产班次名称
     */
    private String productionShiftName;
    /**
     * 生产班组名称
     */
    private String productionTeamName;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品规格
     */
    private String productSpec;

    /**
     * 产品类型
     * FINISHED_PRODUCT 成品
     * SEMI_FINISHED_PRODUCT 半成品
     */
    private String  productType;

}