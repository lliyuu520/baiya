package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 策略：处理成品订单编码字段
 */
public class FinishedOrderCodeStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理成品订单编码字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String finishedOrderNo = context.getFinishedOrderNo();
        if (StrUtil.isBlank(finishedOrderNo)) {
            return StrUtil.EMPTY;
        }
        Integer indexBegin = context.getIndexBegin();
        Integer indexEnd = context.getIndexEnd();
        String encodeType = context.getEncodeType();
        String str = getSubStr(finishedOrderNo, indexBegin, indexEnd);
        if (StrUtil.isNotBlank(encodeType)) {
            str = getEncodeStr(str, encodeType);
        }

        return str;
    }
}