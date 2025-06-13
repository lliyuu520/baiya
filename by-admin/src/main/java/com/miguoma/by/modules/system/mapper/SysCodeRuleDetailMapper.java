package com.miguoma.by.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.modules.system.entity.SysCodeRuleDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 编码规则明细Mapper接口
 */
@Mapper
public interface SysCodeRuleDetailMapper extends BaseMapper<SysCodeRuleDetail> {
    /**
     * 根据规则ID查询编码规则明细
     * 
     * @param ruleId 规则ID
     * @return
     */
    default List<SysCodeRuleDetail> selectListByRuleIdSAndType(Long ruleId, String type) {
        final LambdaQueryWrapper<SysCodeRuleDetail> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(SysCodeRuleDetail::getRuleId, ruleId)
                .eq(SysCodeRuleDetail::getRuleType, type);
        return selectList(lambdaQuery);
    }

    /**
     * 根据规则ID删除编码规则明细
     * @param ruleId
     */
    void deleteByRuleId(@Param("ruleId") Long ruleId);
}

   
