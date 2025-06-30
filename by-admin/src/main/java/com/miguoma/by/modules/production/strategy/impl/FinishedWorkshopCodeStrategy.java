package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 成品产线编码策略
 * 对应sourceField: FINISHED_WORKSHOP_CODE
 */
public class FinishedWorkshopCodeStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理成品产线编码字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String finishedProductionWorkshopCode = context.getFinishedProductionWorkshopCode();
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