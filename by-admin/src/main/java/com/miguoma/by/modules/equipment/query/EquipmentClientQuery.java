package com.miguoma.by.modules.equipment.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APK查询对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipmentClientQuery extends BaseQuery {

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


    /** 工厂编码 */
    private String factoryNo;


}