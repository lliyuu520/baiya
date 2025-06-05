package com.miguoma.by.modules.record.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 件码查询条件
 * 
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecordBoxCodeQuery extends BaseQuery {
    /** 件码 */

    private String code;

}
