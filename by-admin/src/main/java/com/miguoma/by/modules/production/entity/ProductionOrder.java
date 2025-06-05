package com.miguoma.by.modules.production.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.miguoma.by.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 生产订单
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductionOrder extends BaseEntity {

   

    /**
     * 单据编号
     */
    private String orderNo;
    

    /** 单据日期 */
    private LocalDate orderDate;
    /**
     * 生产日期
     */
    private LocalDate productionDate;
    /**
     * 生产部门 编码
     */
    private String productionDepartCode;
    /**
     * 生产车间 编码
     */
    private String productionWorkshopCode;

    /**
     * 生产班次 编码
     */
    private String productionShiftCode;

    /**
     * 生产班组 编码
     */
    private String productionTeamCode;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 件数
     */
    private Integer boxNum;
    /**
     * 片数
     */
    private Integer bagNum;
    /**
     * 最大件数
     */
    private Integer boxNumMaxLimited;

    /**
     * 最大片数
     */
    private Integer bagNumMaxLimited;
    
    
    /**
     * 返工标识
     */
    private Boolean reworkFlag;
    /**
     * 箱号
     */
    private Integer boxNo;
    /**
     * 产品类型
     */
    private String productType;
}