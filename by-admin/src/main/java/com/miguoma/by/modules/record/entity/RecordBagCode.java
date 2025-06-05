package com.miguoma.by.modules.record.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 袋码
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordBagCode extends BaseEntity {
    /**
     * 成品订单号
     */
    private Long finishedProductOrderId;
    
    /**
     * 半成品订单号
     */
    private Long  semiFinishedProductOrderId;
        
    /**
     * 码
     */
    private String code;
    /**
     * 上传时间
     */
    private LocalDateTime uploadDateTime;
    /**
     * 拉码时间
     */
    private LocalDateTime pullDateTime;
    /**
     * 编码规则id
     */
    private Long ruleId;
    /**
     * 箱码
     */
    private String boxCode;
}
