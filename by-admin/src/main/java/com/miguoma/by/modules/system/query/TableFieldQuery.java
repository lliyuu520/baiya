package com.miguoma.by.modules.system.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字段配置查询
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableFieldQuery extends BaseQuery {

    /**
     * 表配置id
     */
    private Long tableConfigId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段描述
     */
    private String fieldComment;
}
