package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 半成品车间编码策略
 * 对应sourceField: SEMI_FINISHED_DEPART_CODE
 */
public class SemiFinishedDepartCodeStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理半成品车间编码字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String semiFinishedProductionDepartCode = context.getSemiFinishedProductionDepartCode();
        Integer indexBegin = context.getIndexBegin();
        Integer indexEnd = context.getIndexEnd();
        String encodeType = context.getEncodeType();
        String str = getSubStr(semiFinishedProductionDepartCode, indexBegin, indexEnd);
        if (StrUtil.isNotBlank(encodeType)) {
            str = getEncodeStr(str, encodeType);
        }
        return str;
    }
}