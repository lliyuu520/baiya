package com.miguoma.by.modules.equipment.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备监控查询
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipmentMonitorQuery extends BaseQuery {

    /**
     * 车间名称
     */
    private String departNo;

    /**
     * 产线标识
     */
    private String workshopNo;
    /**
     * 订单号
     */
    private String orderNo;


}