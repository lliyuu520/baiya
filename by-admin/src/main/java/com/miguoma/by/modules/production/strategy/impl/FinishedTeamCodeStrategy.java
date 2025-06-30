package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 策略：处理成品班次编码字段
 */
public class FinishedTeamCodeStrategy implements BaseCodeFieldStrategy {
    @Override
    public String apply(CodeFieldContext context) {
        String finishedProductionWorkshopCode = context.getFinishedProductionWorkshopCode();
      // 取首字母
      String str = StrUtil.sub(finishedProductionWorkshopCode, 0, 1);
     return str;
    }
}