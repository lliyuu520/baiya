package com.miguoma.by.modules.production.strategy.impl;

import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

import cn.hutool.core.util.StrUtil;

/**
 * 策略：处理箱号字段
 */
public class BoxNoStrategy implements BaseCodeFieldStrategy {
    /**
     * 箱号字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        Integer length = context.getLength();
        if (length == null || length <= 0) {
            return StrUtil.EMPTY;
        }
        // 箱号实际给不出来,需要后面来填充,这里预留一个占位符,且把长度给出来
        // 箱号占位符
        return "{" + "BOX_NO" + ":" + length + "}";
    }
}