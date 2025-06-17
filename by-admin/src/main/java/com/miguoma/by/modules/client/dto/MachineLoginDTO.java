package com.miguoma.by.modules.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 产线登录DTO
 * @author liliangyu
 */
@Data
public class MachineLoginDTO implements Serializable {

    /** 产线编码 */
    private String productionWorkshopCode;


    /** 工厂编码 */
    private String productionFactoryCode;

    /**
     * 机台号
     */
    private String machineNo;

    /**
     * mac地址
     */
    private String macAddress;

    /**
     * ip
     */
    private String ip;









}
