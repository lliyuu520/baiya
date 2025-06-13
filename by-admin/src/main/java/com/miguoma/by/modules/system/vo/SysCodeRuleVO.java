package com.miguoma.by.modules.system.vo;

import com.miguoma.by.modules.system.entity.SysCodeRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 编码规则视图对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCodeRuleVO extends SysCodeRule {
    /**
     * 箱子编码规则明细
     */
    List<SysCodeRuleDetailVO> boxCodeRuleList   = new ArrayList<>();

    /**
     * 内箱编码规则明细
     */
    List<SysCodeRuleDetailVO> innerBoxCodeRuleList = new ArrayList<>();

    /**
     * 袋子编码规则明细
     */
    List<SysCodeRuleDetailVO> bagCodeRuleList = new ArrayList<>();

    /**
     * 万用码规则明细
     */
    List<SysCodeRuleDetailVO> universalCodeRuleList = new ArrayList<>();
    
}
