package com.miguoma.by.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.modules.system.entity.SysCodeRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 编码规则Mapper接口
 */
@Mapper
public interface SysCodeRuleMapper extends BaseMapper<SysCodeRule> {
    
    /**
     * 获取当前生效的编码规则
     * @return
     */ 
    default SysCodeRule selectEnabled() {
        final LambdaQueryWrapper<SysCodeRule> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysCodeRule::getEnabled,  true);
        return selectOne(queryWrapper);

    }
} 
