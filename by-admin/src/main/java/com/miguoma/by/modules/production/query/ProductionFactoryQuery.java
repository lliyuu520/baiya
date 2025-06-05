package com.miguoma.by.modules.production.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工厂查询对象
 * 用于封装工厂查询条件
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionFactoryQuery extends BaseQuery {
    /**
     * 工厂编码
     */
    private String code;

    /**
     * 工厂名称
     */
    private String name;

   
}
