package com.miguoma.by.modules.equipment.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 采集软件管理实体类
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipmentClient extends BaseEntity {
    /**
     * 密码
     */
    private String password;

    /**
     * mac地址
     */
    private String macAddress;
    /**
     * ip
     */
    private String ip;
    /**
     * 机台
     */
    private String machineNo;

    /** 产线编码 */
    private String workshopNo;
    /**
     * 车间编码
     */
    private String departNo;

    /** 工厂编码 */
    private String factoryNo;

}
