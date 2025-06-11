package com.miguoma.by.modules.record.vo;

import com.miguoma.by.modules.record.entity.RecordBoxCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 二维码关联视图对象
 * 用于前端展示
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RecordBoxCodeVO extends RecordBoxCode {
    /**
     * 成品订单号
     */
    private String finishedOrderNo;

    /**
     * 半成品订单号
     */
    private String semiFinishedOrderNo;

} 
