package com.miguoma.by.modules.record.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 件码
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordBoxCode  extends BaseEntity {
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
     * 拉码类型
     */
    private String pullType;

    /**
     * 编码规则id
     */
    private Long ruleId;
    /**
     * 组垛时间
     */
    private LocalDateTime cribDateTime;
    /**
     * 垛码
     */
    private String cribCode;

    /**
     * 内码
     */
    private String innerCode;
   
}
