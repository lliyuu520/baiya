package com.miguoma.by.modules.production.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;

/**
 * 半成品班次编码策略
 * 对应sourceField: SEMI_FINISHED_TEAM_CODE
 */
public class SemiFinishedTeamCodeStrategy implements BaseCodeFieldStrategy {
    /**
     * 处理半成品班次编码字段
     * 
     * @param context
     * @return
     */
    @Override
    public String apply(CodeFieldContext context) {
        String semiFinishedTeamCode = context.getSemiFinishedTeamCode();
        // 取首字母
        String str = StrUtil.sub(semiFinishedTeamCode, 0, 1);
        return str;
    }
}