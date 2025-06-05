package com.miguoma.by.modules.production.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产线查询对象
 * 用于封装产线查询条件
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionShiftQuery extends BaseQuery {
    /**
     * 产线编码
     */
    private String code;

    /**
     * 产线名称
     */
    private String name;


}
