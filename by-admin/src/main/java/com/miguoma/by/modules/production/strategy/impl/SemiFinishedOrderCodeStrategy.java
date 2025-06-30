package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 半成品订单编码策略
 * 对应sourceField: SEMI_FINISHED_ORDER_CODE
 */
public class SemiFinishedOrderCodeStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理半成品订单编码字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String semiFinishedOrderNo = context.getSemiFinishedOrderNo();
        Integer indexBegin = context.getIndexBegin();
        Integer indexEnd = context.getIndexEnd();
        String encodeType = context.getEncodeType();
        String str = getSubStr(semiFinishedOrderNo, indexBegin, indexEnd);
        if (StrUtil.isNotBlank(encodeType)) {
            str = getEncodeStr(str, encodeType);
        }
        return str;
    }
}