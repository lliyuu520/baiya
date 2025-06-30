package com.miguoma.by.modules.production.strategy;

import java.time.LocalDate;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base62;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 策略：处理成品生产日期字段
 */
public class FinishedProductionDateStrategy implements CodeFieldStrategy {
    @Override
    public String apply(CodeFieldContext context) {
        LocalDate finishedProductionDate = context.getFinishedProductionDate();
        final Integer offsetYears = context.getOffsetYears();
        LocalDate limitedUseDate = finishedProductionDate.plusYears(offsetYears);
        return LocalDateTimeUtil.format(limitedUseDate,  DatePattern.PURE_DATE_PATTERN);

    }
}