package com.miguoma.by.modules.production.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班组Query
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionTeamQuery extends BaseQuery {
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
}
