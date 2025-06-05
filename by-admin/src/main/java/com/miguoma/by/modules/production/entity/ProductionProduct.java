package com.miguoma.by.modules.production.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.miguoma.by.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductionProduct extends BaseEntity {

    /**
     * 产品编码
     */
    private String code;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品类别编码
     */
    private String categoryCode;
    /**
     * 产品类型
     */
    private String productType;



    /**
     * 单位
     */
    private String unit;


    /**
     * 一箱包数
     */
    private Integer oneBoxPackageNum;

    /**
     * 货号
     */
    private String goodsCode;


    /**
     * 条形码
     */
    private String barCode;

    /**
     * 规格
     */
    private String specification;
}