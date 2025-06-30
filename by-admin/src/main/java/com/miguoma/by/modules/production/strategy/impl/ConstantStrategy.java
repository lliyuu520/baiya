package com.miguoma.by.modules.production.strategy.impl;

import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 策略：处理常量字段
 */
public class ConstantStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理常量字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        return context.getConstant();
    }
}