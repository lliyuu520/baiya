package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 成品产品编码策略
 * 对应sourceField: FINISHED_PRODUCT_CODE
 */
public class FinishedProductCodeStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理成品产品编码字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String finishedProductCode = context.getFinishedProductCode();
        if (StrUtil.isBlank(finishedProductCode)) {
            return StrUtil.EMPTY;
        }
        Integer indexBegin = context.getIndexBegin();
        Integer indexEnd = context.getIndexEnd();
        String encodeType = context.getEncodeType();
        String str = getSubStr(finishedProductCode, indexBegin, indexEnd);
        if (StrUtil.isNotBlank(encodeType)) {
            str = getEncodeStr(str, encodeType);
        }
        return str;
    }
}