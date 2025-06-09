package com.miguoma.by.modules.production.query;

import cn.hutool.core.util.ArrayUtil;
import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 产品查询对象 用于封装产品查询条件
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionOrderQuery extends BaseQuery {
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单类型
     */
    private String productType;
    /**
     * 生产车间名称
     */
    private String productionWorkshopName;

    /**
     * 生产车间编号
     */
    private String productionWorkshopCode;

    /**
     * 单据日期范围
     */
    private LocalDate[] orderDateRange;
    
    /**
     * 单据日期开始
     */
    private LocalDate orderDateBegin;
    
    /**
     * 单据日期结束
     */
    private LocalDate orderDateEnd;
    /**
     * 返工标识
     */
    private Boolean reworkFlag;

    public LocalDate getOrderDateBegin() {
        if(ArrayUtil.isNotEmpty(orderDateRange)){
            return orderDateRange[0];
        }
        return orderDateBegin;
    }

    public LocalDate getOrderDateEnd() {
        if(ArrayUtil.isNotEmpty(orderDateRange)){
            return orderDateRange[1];
        }
        return orderDateEnd;
    }
}