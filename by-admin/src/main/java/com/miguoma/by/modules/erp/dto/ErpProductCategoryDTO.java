package com.miguoma.by.modules.erp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 产品类别数据传输对象
 */
@Data
public class ErpProductCategoryDTO implements Serializable {


    /**
     * 类别编码
     */
    private String code;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 父类别编码
     */
    private String parentCode;
}