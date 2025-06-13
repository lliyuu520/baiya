package com.miguoma.by.modules.erp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单数据传输对象
 *
 */
@Data
public class ErpOrderDTO implements Serializable {

    /**
     * 单据编号
     */
    private String orderNo;


    /** 单据日期 */
    private String orderDate;
    /**
     * 生产日期
     */
    private String productionDate;

    /**
     * 生产部门 编码
     */
    private String productionDepartCode;
    /**
     * 生产车间 编码
     */
    private String productionWorkshopCode;

    /**
     * 生产班组 编码
     */
    private String productionShiftCode;

    /**
     * 生产班次 编码
     */
    private String productionTeamCode;

    /**
     * 产品编码
     */
    private String productCode;


    /**
     * 件数上限
     */
    private String boxNumMaxLimited;
    /**
     * 件数
     */
    private String boxNum;

    /**
     * 包数上限
     */
    private String bagNumMaxLimited;


    /**
     * 包数
     */
    private String bagNum;


}