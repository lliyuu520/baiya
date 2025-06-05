package com.miguoma.by.modules.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 生成编码DTO
 * 
 * @author lliyuu520
 */
@Data
public class PullCodeDTO implements Serializable {
    /**
     * 成品订单ID
     */
    private Long finishedProductOrderId;
    
    /**
     * 半成品订单ID
     */
    
    private Long semiFinishedProductOrderId;
    
    /**
     * 箱号开始
     */
    private Integer boxNoBegin;
    /**
     * 箱码个数
     */
    private Integer boxCodeNum;

    /**
     * 类型 QR_CODE, LOGISTICS_CODE
     */
    private String type;

}
