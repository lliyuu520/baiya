package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 半成品产线编码策略
 * 对应sourceField: SEMI_FINISHED_WORKSHOP_CODE
 */
public class SemiFinishedWorkshopCodeStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理半成品产线编码字段
     *
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String semiFinishedProductionWorkshopCode = context.getSemiFinishedProductionWorkshopCode();
        if (StrUtil.isBlank(semiFinishedProductionWorkshopCode)) {
            return StrUtil.EMPTY;
        }
        Integer indexBegin = context.getIndexBegin();
        Integer indexEnd = context.getIndexEnd();
        String encodeType = context.getEncodeType();
        String str = getSubStr(semiFinishedProductionWorkshopCode, indexBegin, indexEnd);
        if (StrUtil.isNotBlank(encodeType)) {
            str = getEncodeStr(str, encodeType);
        }
        return str;
    }
}