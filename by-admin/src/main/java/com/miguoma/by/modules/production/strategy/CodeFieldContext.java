package com.miguoma.by.modules.production.strategy;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 码字段处理上下文
 * 封装所有拼接策略可能需要的参数
 */
@Data
public class CodeFieldContext implements Serializable {
    // 成品字段

    /**
     * 成品生产日期
     */
    private LocalDate finishedProductionDate;
    /**
     * 成品生产车间
     */
    private String finishedProductionDepartCode;
    /**
     * 成品生产产线
     */
    private String finishedProductionWorkshopCode;
    /**
     * 成品班次编码
     */
    private String finishedTeamCode;
    /**
     * 成品订单编号
     */
    private String finishedOrderNo;
    /**
     * 成品产品编码
     */
    private String finishedProductCode;


    // 半成品字段
    /**
     * 半成品生产日期
     */
    private LocalDate semiFinishedProductionDate;

    /**
     * 半成品生产部门
     */
    private String semiFinishedProductionDepartCode;

    /**
     * 半成品生产车间
     */
    private String semiFinishedProductionWorkshopCode;

    /**
     * 半成品班次编码
     */
    private String semiFinishedTeamCode;

    /**
     * 半成品订单编号
     */
    private String semiFinishedOrderNo;

    /**
     * 半成品产品编码
     */
    private String semiFinishedProductCode;


    // 当前规则明细字段
    private String sourceField;

    /**
     * 长度
     */
    private Integer length;
    /**
     * 常量字段
     */

    private String constant;
    /**
     * 开始索引
     */
    private Integer indexBegin;
    /**
     * 结束所有
     */
    private Integer indexEnd;
    /**
     * 编码类型
     */
    private String encodeType;
    /**
     * 偏移年份
     */
    private Integer offsetYears;
    /**
     * 随机字符串类型
     */
    private String randomType;

    /**
     * 箱号
     */
    private String boxNo;


}