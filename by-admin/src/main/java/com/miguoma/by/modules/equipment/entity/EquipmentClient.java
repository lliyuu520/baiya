package com.miguoma.by.modules.equipment.entity;

import com.miguoma.by.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APK管理实体类
 * 用于存储APK版本信息，包括版本号、下载地址等
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
    private String departCode;


    /** 工厂编码 */
    private String factoryNo;

}
