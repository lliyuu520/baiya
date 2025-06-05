package com.miguoma.by.modules.record.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 袋码查询条件
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecordBagCodeQuery extends BaseQuery {


    /**袋码 */
    private String code;
    
    
   
} 
