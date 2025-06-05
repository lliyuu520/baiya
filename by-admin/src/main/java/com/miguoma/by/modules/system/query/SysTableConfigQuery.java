package com.miguoma.by.modules.system.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表配置查询
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTableConfigQuery extends BaseQuery {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

}
