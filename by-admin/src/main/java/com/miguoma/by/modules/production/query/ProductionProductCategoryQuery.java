package com.miguoma.by.modules.production.query;

import com.miguoma.by.common.base.query.BaseQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品分类查询对象
 * 用于封装产品分类查询条件
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionProductCategoryQuery extends BaseQuery {

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