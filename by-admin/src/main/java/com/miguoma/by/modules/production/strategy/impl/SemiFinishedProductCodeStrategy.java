package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 半成品产品编码策略
 * 对应sourceField: SEMI_FINISHED_PRODUCT_CODE
 */
public class SemiFinishedProductCodeStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理半成品产品编码字段
     *
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String semiFinishedProductCode = context.getSemiFinishedProductCode();
        if (StrUtil.isBlank(semiFinishedProductCode)) {
            return StrUtil.EMPTY;
        }
        Integer indexBegin = context.getIndexBegin();
        Integer indexEnd = context.getIndexEnd();
        String encodeType = context.getEncodeType();
        String str = getSubStr(semiFinishedProductCode, indexBegin, indexEnd);
        if (StrUtil.isNotBlank(encodeType)) {
            str = getEncodeStr(str, encodeType);
        }
        return str;
    }
}