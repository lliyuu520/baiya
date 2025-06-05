package com.miguoma.by.modules.record.vo;

import com.miguoma.by.modules.record.entity.RecordBagCode;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 袋码关联视图对象
 * 用于前端展示
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecordBagCodeVO extends RecordBagCode {
    /**
     * 成品订单号
     */
    private String finishedOrderNo;

    /**
     * 半成品订单号
     */
    private String semiFinishedOrderNo;
}
