package com.miguoma.by.modules.production.strategy.impl;

import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

import java.time.LocalDate;

/**
 * 策略：处理成品生产日期字段
 */
public class SemiFinishedProductionDateStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理半成品生产日期字段
     * 
     * @param context 处理上下文（包含所有需要的参数，如订单、产品、规则、index等）
     * @return 编码后的字符串
     */
    @Override
    public String apply(CodeFieldContext context) {
        LocalDate semiFinishedProductionDate = context.getSemiFinishedProductionDate();
        final Integer offsetYears = context.getOffsetYears();
        String offsetYearsStr = getOffsetYears(semiFinishedProductionDate, offsetYears);
        return offsetYearsStr;
    }
}