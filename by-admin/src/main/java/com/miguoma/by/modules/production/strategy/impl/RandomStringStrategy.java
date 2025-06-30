package com.miguoma.by.modules.production.strategy.impl;

import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 随机字符串策略
 * 对应sourceField: RANDOM_STRING
 */
public class RandomStringStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理随机字符串字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String randomType = context.getRandomType();
        Integer length = context.getLength();
        return "{" + randomType + ":" + length + "}";
    }
}