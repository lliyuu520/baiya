package com.miguoma.by.modules.equipment.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备监控数据传输对象
 */
@Data
public class EquipmentMonitorDTO implements Serializable {

    /**
     * 成品订单号
     */
    private Long finishedProductOrderId;

    /**
     * 上传箱码数量
     */
    private Integer uploadBoxCodeNum;

    /**
     * 步骤名称
     */
    private String stepName;


}