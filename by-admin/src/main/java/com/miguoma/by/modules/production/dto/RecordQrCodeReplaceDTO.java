package com.miguoma.by.modules.production.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecordQrCodeReplaceDTO {

    private Long id;

    /**
     * 生产订单ID
     */
    private Long productionOrderId;

    /**
     * 生产订单编号
     */
    private String productionOrderNo;

    /**
     * 原二维码
     */
    private String originalQrCode;

    /**
     * 新二维码
     */
    private String newQrCode;

    /**
     * 替换原因
     */
    private String replaceReason;

    /**
     * 替换时间
     */
    private LocalDateTime replaceTime;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 备注
     */
    private String remark;
}