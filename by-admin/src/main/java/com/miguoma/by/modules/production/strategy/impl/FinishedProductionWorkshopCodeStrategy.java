package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 策略：处理成品产线编码字段
 */
public class FinishedProductionWorkshopCodeStrategy implements BaseCodeFieldStrategy {
    @Override
    public String apply(CodeFieldContext context) {
        String finishedProductionWorkshopCode = context.getFinishedProductionWorkshopCode();
        if (StrUtil.isBlank(finishedProductionWorkshopCode)) {
            return StrUtil.EMPTY;
        }
        Integer indexBegin = context.getIndexBegin();
        Integer indexEnd = context.getIndexEnd();
        String encodeType = context.getEncodeType();
        String str = getSubStr(finishedProductionWorkshopCode, indexBegin, indexEnd);
        if (StrUtil.isNotBlank(encodeType)) {
            str = getEncodeStr(str, encodeType);
        }
        return str;
    }
}