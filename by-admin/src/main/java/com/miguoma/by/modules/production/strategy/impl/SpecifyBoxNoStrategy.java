package com.miguoma.by.modules.production.strategy.impl;

import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 指定箱号策略
 * 对应sourceField: SPECIFY_BOX_NO
 */
public class SpecifyBoxNoStrategy implements BaseCodeFieldStrategy {
    @Override
    public String apply(CodeFieldContext context) {
        String specifyBoxNo = context.getSpecifyBoxNo();
        return specifyBoxNo;
    }
}