package com.miguoma.by.modules.production.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品分类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductionProductCategory extends BaseEntity {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类编码
     */
    private String code;

    /**
     * 父级编码
     */
    private String parentCode;
}