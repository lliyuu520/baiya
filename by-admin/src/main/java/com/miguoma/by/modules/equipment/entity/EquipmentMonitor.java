package com.miguoma.by.modules.equipment.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 设备监控
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipmentMonitor extends BaseEntity {

    /**
     * 车间名称
     */
    private String departNo;

    /**
     * 产线标识
     */
    private String workshopNo;
    /**
     * 订单生产日期
     */
    private LocalDate orderProductionDate;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 步骤名称
     */
    private String stepName;

    /**
     * 机器上传箱数
     */
    private Integer machineUploadBoxNum;

    /**
     * 机器上传袋数
     */
    private Integer machineUploadBagNum;

    /**
     * 服务器上传箱数
     */
    private Integer serverUploadBoxNum;

    /**
     * 服务器上传袋数
     */
    private Integer serverUploadBagNum;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 信息
     */
    private String info;

    /**
     * 同步箱码数量
     */
    private Integer syncBoxCodeNum;

    /**
     * 处理时间
     */
    private LocalDateTime handleDateTime;


}
