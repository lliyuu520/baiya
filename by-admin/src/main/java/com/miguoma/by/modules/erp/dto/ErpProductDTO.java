package com.miguoma.by.modules.erp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 产品数据传输对象
 */
@Data
public class ErpProductDTO implements Serializable {

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
     * 单位
     */
    private String unit;
    /**
     * 一箱包数
     */
    private String oneBoxPackageNum;

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