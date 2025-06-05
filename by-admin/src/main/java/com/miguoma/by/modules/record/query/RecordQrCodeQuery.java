package com.miguoma.by.modules.record.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 二维码关联对象
 * 
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecordQrCodeQuery extends BaseQuery {
    
    /** e二维码*/

    private String code;
   
} 
