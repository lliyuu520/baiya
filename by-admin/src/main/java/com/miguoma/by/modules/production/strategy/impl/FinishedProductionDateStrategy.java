package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

import java.time.LocalDate;

/**
 * 策略：处理成品生产日期字段
 */
public class FinishedProductionDateStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理成品生产日期字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        LocalDate finishedProductionDate = context.getFinishedProductionDate();
        if (finishedProductionDate == null) {
            return StrUtil.EMPTY;
        }
        final Integer offsetYears = context.getOffsetYears();
        String str = getOffsetYears(finishedProductionDate, offsetYears);
        return str;

    }
}