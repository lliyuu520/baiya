package com.miguoma.by.modules.production.query;

import com.miguoma.by.common.base.query.BaseQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品查询对象 用于封装产品查询条件
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionProductQuery extends BaseQuery {

    /**
     * 产品编码
     */
    private String code;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品类型编码
     */
    private String categoryCode;
}